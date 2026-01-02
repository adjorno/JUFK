package com.ifochka.kotrack

import com.posthog.PostHog
import io.ktor.client.HttpClient

internal actual fun createHttpClient(): HttpClient {
    error("Not used on Android - uses PostHog SDK")
}

internal actual fun getPlatformName(): String = "ANDROID"

internal actual fun getPostHogApiKey(): String? = null // Not needed - SDK initialized separately

actual fun createAnalytics(): Analytics = PostHogAndroidAnalytics()

class PostHogAndroidAnalytics : Analytics {
    private var campaign: String? = null

    override fun trackEvent(
        event: AnalyticsEvent,
        properties: Map<String, Any>,
    ) {
        val props = properties.toMutableMap()
        props["platform"] = "ANDROID"
        campaign?.let { props["campaign"] = it }

        PostHog.capture(event.eventName, properties = props)
    }

    override fun setCampaign(campaign: String?) {
        this.campaign = campaign
    }
}
