package com.ifochka.kotrack

import kotlinx.browser.window

private const val KEY_DISTINCT_ID = "jufk_distinct_id"

internal actual suspend fun saveDistinctId(id: String) {
    window.localStorage.setItem(KEY_DISTINCT_ID, id)
}

internal actual suspend fun loadDistinctId(): String? = window.localStorage.getItem(KEY_DISTINCT_ID)
