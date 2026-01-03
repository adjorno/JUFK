package com.ifochka.kotrack

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class AnalyticsManager(
    private val analytics: Analytics,
) {
    private var distinctId: String? = null

    @OptIn(ExperimentalUuidApi::class)
    suspend fun initialize(appVersion: String) {
        var id = loadDistinctId()
        val props = mapOf("app_version" to appVersion)
        if (id == null) {
            id = Uuid.random().toString()
            saveDistinctId(id)
            analytics.trackEvent(AnalyticsEvent.APP_WELCOME, id, props)
        } else {
            analytics.trackEvent(AnalyticsEvent.APP_RETURN, id, props)
        }
        this.distinctId = id
    }

    fun setCampaign(campaign: String?) {
        analytics.setCampaign(campaign)
    }
}
