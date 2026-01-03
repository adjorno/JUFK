package com.ifochka.kotrack

import kotlinx.coroutines.CoroutineScope

enum class AnalyticsEvent(
    val eventName: String,
) {
    APP_WELCOME("app_welcome"),
    APP_RETURN("app_return"),
}

interface Analytics {
    fun trackEvent(
        event: AnalyticsEvent,
        distinctId: String,
        properties: Map<String, Any>,
    )

    fun setCampaign(campaign: String?)
}

internal expect fun getPlatformName(): String

expect fun createAnalytics(
    apiKey: String,
    coroutineScope: CoroutineScope,
): Analytics
