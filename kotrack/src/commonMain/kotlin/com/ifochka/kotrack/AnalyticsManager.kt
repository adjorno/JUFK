package com.ifochka.kotrack

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class AnalyticsManager(
    private val analytics: Analytics,
) {
    private var distinctId: String? = null

    @OptIn(ExperimentalUuidApi::class)
    suspend fun initialize() {
        var id = loadDistinctId()
        if (id == null) {
            id = Uuid.random().toString()
            saveDistinctId(id)
            analytics.trackEvent(AnalyticsEvent.APP_LAUNCH, id)
        } else {
            analytics.trackEvent(AnalyticsEvent.APP_RETURN, id)
        }
        this.distinctId = id
    }

    fun setCampaign(campaign: String?) {
        analytics.setCampaign(campaign)
    }
}
