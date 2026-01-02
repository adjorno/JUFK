package com.ifochka.kotrack

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class AnalyticsManager(
    private val analytics: Analytics,
) {
    private val mutex = Mutex()
    private var hasLaunched = false

    suspend fun trackAppStart() {
        mutex.withLock {
            if (hasLaunched) {
                println("Analytics: App Return")
                analytics.trackEvent(AnalyticsEvent.APP_RETURN)
            } else {
                hasLaunched = true
                println("Analytics: App Launch")
                analytics.trackEvent(AnalyticsEvent.APP_LAUNCH)
            }
        }
    }

    fun setCampaign(campaign: String?) {
        analytics.setCampaign(campaign)
    }
}
