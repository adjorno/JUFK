package com.ifochka.kotrack

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

internal expect fun createHttpClient(): HttpClient

internal expect fun getDistinctId(): String

internal expect fun saveDistinctId(id: String)

private const val POSTHOG_HOST = "https://us.i.posthog.com"

private fun generateUUID(): String {
    val chars = "0123456789abcdef"
    return buildString {
        repeat(8) { append(chars.random()) }
        append('-')
        repeat(4) { append(chars.random()) }
        append('-')
        append('4') // UUID v4
        repeat(3) { append(chars.random()) }
        append('-')
        append(chars[(8..11).random()]) // 8, 9, a, or b
        repeat(3) { append(chars.random()) }
        append('-')
        repeat(12) { append(chars.random()) }
    }
}

@Serializable
private data class PostHogEvent(
    val api_key: String,
    val event: String,
    val properties: JsonObject,
    val distinct_id: String = "anonymous",
)

internal class PostHogClient : Analytics {
    private val client =
        createHttpClient().config {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    },
                )
            }
        }
    private val scope = CoroutineScope(Dispatchers.Default)
    private var campaign: String? = null
    private val distinctId: String by lazy {
        val stored = getDistinctId()
        if (stored.isNotEmpty()) {
            stored
        } else {
            val newId = generateUUID()
            saveDistinctId(newId)
            newId
        }
    }

    override fun trackEvent(
        event: AnalyticsEvent,
        properties: Map<String, Any>,
    ) {
        val apiKey = getPostHogApiKey() ?: return

        scope.launch {
            try {
                val propsMap = mutableMapOf<String, JsonElement>()
                propsMap["platform"] = JsonPrimitive(getPlatformName())
                campaign?.let { propsMap["campaign"] = JsonPrimitive(it) }
                properties.forEach { (k, v) ->
                    propsMap[k] =
                        when (v) {
                            is String -> JsonPrimitive(v)
                            is Number -> JsonPrimitive(v)
                            is Boolean -> JsonPrimitive(v)
                            else -> JsonPrimitive(v.toString())
                        }
                }

                val payload =
                    PostHogEvent(
                        api_key = apiKey,
                        event = event.eventName,
                        properties = JsonObject(propsMap),
                        distinct_id = distinctId,
                    )

                client.post("$POSTHOG_HOST/capture/") {
                    contentType(ContentType.Application.Json)
                    setBody(payload)
                }
            } catch (_: Exception) {
                // Silently ignore analytics errors
            }
        }
    }

    override fun setCampaign(campaign: String?) {
        this.campaign = campaign
    }
}
