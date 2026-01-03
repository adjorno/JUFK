package com.ifochka.kotrack

import platform.Foundation.NSUserDefaults

private const val KEY_DISTINCT_ID = "distinct_id"

internal actual suspend fun saveDistinctId(id: String) {
    NSUserDefaults.standardUserDefaults.setObject(id, KEY_DISTINCT_ID)
}

internal actual suspend fun loadDistinctId(): String? =
    NSUserDefaults.standardUserDefaults.stringForKey(KEY_DISTINCT_ID)
