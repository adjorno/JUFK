package com.ifochka.kotrack

import com.posthog.PostHog
import com.posthog.PostHogConfig
import io.ktor.client.HttpClient

internal actual fun createHttpClient(): HttpClient {
    error("Not used on Desktop - uses PostHog JVM SDK")
}

internal actual fun getPlatformName(): String = "DESKTOP"

internal actual fun getDistinctId(): String {
    error("Not used on Desktop")
}

internal actual fun saveDistinctId(id: String) {
    error("Not used on Desktop")
}

actual fun createAnalytics(apiKey: String): Analytics = PostHogDesktopAnalytics(apiKey)

class PostHogDesktopAnalytics(
    apiKey: String,
) : Analytics {
    init {
        val config = PostHogConfig(apiKey)
        PostHog.setup(config)
    }

    private var campaign: String? = null

    override fun trackEvent(
        event: AnalyticsEvent,
        properties: Map<String, Any>,
    ) {
        val props = properties.toMutableMap()
        props["platform"] = "DESKTOP"
        campaign?.let { props["campaign"] = it }
        try {
            println("Desktop Analytics: event $event")
            PostHog.capture(event.eventName, properties = props)
        } catch (e: Exception) {
            println("Desktop Analytics: error $e")
            throw e
        }
    }

    override fun setCampaign(campaign: String?) {
        this.campaign = campaign
    }
}
