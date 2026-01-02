package com.ifochka.kotrack

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

object AnalyticsManager {
    private val analytics: Analytics by lazy { createAnalytics() }
    private val mutex = Mutex()
    private var hasLaunched = false

    suspend fun trackAppStart() {
        mutex.withLock {
            if (hasLaunched) {
                analytics.trackEvent(AnalyticsEvent.APP_RETURN)
            } else {
                hasLaunched = true
                analytics.trackEvent(AnalyticsEvent.APP_LAUNCH)
            }
        }
    }

    fun setCampaign(campaign: String?) {
        analytics.setCampaign(campaign)
    }
}
