package com.ifochka.kotrack

import com.posthog.PostHog
import com.posthog.PostHogConfig
import com.posthog.PostHogConfig.Companion.DEFAULT_EU_HOST
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import kotlinx.coroutines.CoroutineScope

internal actual fun createHttpClient(): HttpClient = HttpClient(CIO)

internal actual fun getPlatformName(): String = "DESKTOP"

private var storedDistinctId: String = ""

internal actual fun getDistinctId(): String = storedDistinctId

internal actual fun saveDistinctId(id: String) {
    storedDistinctId = id
}

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
