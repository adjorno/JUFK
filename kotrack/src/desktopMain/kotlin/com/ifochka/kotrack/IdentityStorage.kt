package com.ifochka.kotrack

import java.io.File

private const val FILE_NAME = ".jufk_distinct_id"

internal actual suspend fun saveDistinctId(id: String) {
    val file = File(System.getProperty("java.io.tmpdir"), FILE_NAME)
    file.writeText(id)
}

internal actual suspend fun loadDistinctId(): String? {
    val file = File(System.getProperty("java.io.tmpdir"), FILE_NAME)
    return if (file.exists()) file.readText().trim() else null
}
