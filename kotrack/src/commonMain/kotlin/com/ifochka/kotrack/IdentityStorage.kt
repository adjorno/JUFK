package com.ifochka.kotrack

internal expect suspend fun saveDistinctId(id: String)

internal expect suspend fun loadDistinctId(): String?
