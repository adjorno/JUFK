package com.ifochka.kotrack

import com.posthog.PostHog
import com.posthog.android.PostHogAndroid
import com.posthog.android.PostHogAndroidConfig
import io.ktor.client.HttpClient

internal actual fun createHttpClient(): HttpClient {
    error("Not used on Android - uses PostHog SDK")
}

internal actual fun getPlatformName(): String = "ANDROID"

internal actual fun getDistinctId(): String {
    error("Not used on Android")
}

internal actual fun saveDistinctId(id: String) {
    error("Not used on Android")
}

actual fun createAnalytics(apiKey: String): Analytics = PostHogAndroidAnalytics(apiKey)

class PostHogAndroidAnalytics(
    apiKey: String,
) : Analytics {
    init {
        val config = PostHogAndroidConfig(
            apiKey = apiKey,
            host = POSTHOG_HOST,
        )
        PostHogAndroid.setup(AppContext.get(), config)
    }

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
