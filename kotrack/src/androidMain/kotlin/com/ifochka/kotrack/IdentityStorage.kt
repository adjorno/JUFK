package com.ifochka.kotrack

private const val PREFS_NAME = "jufk_analytics"
private const val KEY_DISTINCT_ID = "distinct_id"

internal actual suspend fun saveDistinctId(id: String) {
    val prefs = AppContext.get().getSharedPreferences(PREFS_NAME, android.content.Context.MODE_PRIVATE)
    prefs.edit().putString(KEY_DISTINCT_ID, id).apply()
}

internal actual suspend fun loadDistinctId(): String? {
    val prefs = AppContext.get().getSharedPreferences(PREFS_NAME, android.content.Context.MODE_PRIVATE)
    return prefs.getString(KEY_DISTINCT_ID, null)
}
