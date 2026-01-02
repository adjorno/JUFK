package com.ifochka.kotrack

import kotlinx.coroutines.CoroutineScope

enum class AnalyticsEvent(
    val eventName: String,
) {
    APP_LAUNCH("app_launch"),
    APP_RETURN("app_return"),
}

interface Analytics {
    fun trackEvent(
        event: AnalyticsEvent,
        properties: Map<String, Any> = emptyMap(),
    )

    fun setCampaign(campaign: String?)
}

internal expect fun getPlatformName(): String

expect fun createAnalytics(
    apiKey: String,
    coroutineScope: CoroutineScope,
): Analytics
