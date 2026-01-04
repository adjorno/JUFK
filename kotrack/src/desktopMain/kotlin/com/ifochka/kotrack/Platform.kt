package com.ifochka.kotrack

import com.posthog.PostHog
import com.posthog.PostHogConfig
import com.posthog.PostHogConfig.Companion.DEFAULT_EU_HOST
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import kotlinx.coroutines.CoroutineScope

internal actual fun createHttpClient(): HttpClient = HttpClient(CIO)

public actual fun getPlatformName(): String = "DESKTOP"

actual fun createAnalytics(
    apiKey: String,
    coroutineScope: CoroutineScope,
): Analytics = PostHogClient(apiKey, coroutineScope)

class PostHogDesktopAnalytics(
    apiKey: String,
) : Analytics {
    init {
        val config = PostHogConfig(apiKey, DEFAULT_EU_HOST)
        PostHog.setup(config)
    }

    private var campaign: String? = null

    override fun trackEvent(
        event: AnalyticsEvent,
        distinctId: String,
        properties: Map<String, Any>,
    ) {
        val props = properties.toMutableMap()
        props["platform"] = "DESKTOP"
        campaign?.let { props["campaign"] = it }
        PostHog.capture(
            event = event.eventName,
            distinctId = distinctId,
            properties = props,
        )
    }

    override fun setCampaign(campaign: String?) {
        this.campaign = campaign
    }
}
