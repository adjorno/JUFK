package com.ifochka.kotrack

object AnalyticsManager {
    private val analytics: Analytics by lazy { createAnalytics() }
    private var hasLaunched = false

    fun trackAppStart() {
        if (hasLaunched) {
            analytics.trackEvent(AnalyticsEvent.APP_RETURN)
        } else {
            hasLaunched = true
            analytics.trackEvent(AnalyticsEvent.APP_LAUNCH)
        }
    }

    fun setCampaign(campaign: String?) {
        analytics.setCampaign(campaign)
    }
}
