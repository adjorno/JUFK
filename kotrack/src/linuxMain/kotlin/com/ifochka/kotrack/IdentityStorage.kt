package com.ifochka.kotrack

internal actual suspend fun saveDistinctId(id: String) { saveDistinctIdToTempKotlinIO(id) }

internal actual suspend fun loadDistinctId() = loadDistinctIdFromKotlinIO()
