package com.ifochka.kotrack

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal expect fun createHttpClient(): HttpClient

private const val POSTHOG_HOST = "https://us.i.posthog.com"

internal class PostHogClient : Analytics {
    private val client = createHttpClient()
    private val scope = CoroutineScope(Dispatchers.Default)
    private var campaign: String? = null

    override fun trackEvent(
        event: AnalyticsEvent,
        properties: Map<String, Any>,
    ) {
        val apiKey = getPostHogApiKey() ?: return

        scope.launch {
            try {
                val propsJson = buildString {
                    append("{")
                    append("\"platform\":\"${getPlatformName()}\"")
                    campaign?.let { append(",\"campaign\":\"$it\"") }
                    properties.forEach { (k, v) ->
                        append(",\"$k\":\"$v\"")
                    }
                    append("}")
                }

                val body =
                    """{"api_key":"$apiKey","event":"${event.eventName}","properties":$propsJson,"distinct_id":"anonymous"}"""

                client.post("$POSTHOG_HOST/capture/") {
                    contentType(ContentType.Application.Json)
                    setBody(body)
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
