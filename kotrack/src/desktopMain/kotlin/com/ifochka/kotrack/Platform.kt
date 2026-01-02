package com.ifochka.kotrack

import com.posthog.PostHog
import io.ktor.client.HttpClient

internal actual fun createHttpClient(): HttpClient {
    error("Not used on Desktop - uses PostHog JVM SDK")
}

internal actual fun getPlatformName(): String = "DESKTOP"

internal actual fun getPostHogApiKey(): String? = null // Not needed - SDK initialized separately

internal actual fun getDistinctId(): String {
    error("Not used on Desktop")
}

internal actual fun saveDistinctId(id: String) {
    error("Not used on Desktop")
}

actual fun createAnalytics(): Analytics = PostHogDesktopAnalytics()

class PostHogDesktopAnalytics : Analytics {
    private var campaign: String? = null

    override fun trackEvent(
        event: AnalyticsEvent,
        properties: Map<String, Any>,
    ) {
        val props = properties.toMutableMap()
        props["platform"] = "DESKTOP"
        campaign?.let { props["campaign"] = it }

        PostHog.capture(event.eventName, properties = props)
    }

    override fun setCampaign(campaign: String?) {
        this.campaign = campaign
    }
}
