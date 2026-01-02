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
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

internal expect fun createHttpClient(): HttpClient

internal expect fun getDistinctId(): String

internal expect fun saveDistinctId(id: String)

internal const val POSTHOG_HOST = "https://eu.i.posthog.com"

@Serializable
private data class PostHogEvent(
    @SerialName("api_key")
    val apiKey: String,
    val event: String,
    val properties: JsonObject,
    @SerialName("distinct_id")
    val distinctId: String,
)

internal class PostHogClient(
    private val apiKey: String,
    private val coroutineScope: CoroutineScope,
) : CoroutineScope by coroutineScope,
    Analytics {
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

    private var campaign: String? = null

    @OptIn(ExperimentalUuidApi::class)
    private val distinctId: String by lazy {
        val stored = getDistinctId()
        stored.ifEmpty {
            Uuid.random().toString().also {
                saveDistinctId(it)
            }
        }
    }

    override fun trackEvent(
        event: AnalyticsEvent,
        properties: Map<String, Any>,
    ) {
        launch {
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
                        apiKey = apiKey,
                        event = event.eventName,
                        properties = JsonObject(propsMap),
                        distinctId = distinctId,
                    )

                client.post("$POSTHOG_HOST/capture/") {
                    contentType(ContentType.Application.Json)
                    setBody(payload)
                }
            } catch (e: Exception) {
                // Silently ignore analytics errors
            }
        }
    }

    override fun setCampaign(campaign: String?) {
        this.campaign = campaign
    }
}
