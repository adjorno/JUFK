package com.ifochka.kotrack

import com.posthog.PostHog
import com.posthog.android.PostHogAndroid
import com.posthog.android.PostHogAndroidConfig
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope

internal actual fun createHttpClient(): HttpClient {
    error("Not used on Android - uses PostHog SDK")
}

public actual fun getPlatformName(): String = "ANDROID"

actual fun createAnalytics(
    apiKey: String,
    coroutineScope: CoroutineScope,
): Analytics = PostHogAndroidAnalytics(apiKey, coroutineScope)

class PostHogAndroidAnalytics(
    apiKey: String,
    coroutineScope: CoroutineScope,
) : CoroutineScope by coroutineScope,
    Analytics {
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
        distinctId: String,
        properties: Map<String, Any>,
    ) {
        PostHog.identify(distinctId)
        val props = properties.toMutableMap()
        props["platform"] = "ANDROID"
        campaign?.let { props["campaign"] = it }

        PostHog.capture(event.eventName, properties = props)
    }

    override fun setCampaign(campaign: String?) {
        this.campaign = campaign
    }
}
