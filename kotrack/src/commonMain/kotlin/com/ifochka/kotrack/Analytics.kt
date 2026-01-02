package com.ifochka.kotrack

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

internal expect fun getPostHogApiKey(): String?

expect fun createAnalytics(): Analytics
