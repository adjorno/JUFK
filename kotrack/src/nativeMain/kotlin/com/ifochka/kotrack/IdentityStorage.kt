@file:OptIn(ExperimentalForeignApi::class)

package com.ifochka.kotrack

import io.ktor.utils.io.readText
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toKString
import kotlinx.io.Buffer
import kotlinx.io.Source
import kotlinx.io.buffered
import kotlinx.io.files.FileSystem
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.files.SystemTemporaryDirectory
import platform.posix.getenv // Used to get environment variables like HOME

private const val ID_FILENAME = ".jufk_analytics_distinct_id"

// Get the Path to the storage file in the user's home directory
private fun getDistinctIdPath(): Path? = Path(SystemTemporaryDirectory, ID_FILENAME)

internal suspend fun saveDistinctIdToTempKotlinIO(id: String) {
    val path = getDistinctIdPath() ?: return

    try {
        val bytes = id.encodeToByteArray()
        SystemFileSystem.sink(path).use { sink ->
            val buffer = Buffer()
            buffer.write(bytes, 0, bytes.size)
            sink.write(buffer, buffer.size)
        }
    } catch (e: Exception) {
        // Log the error or handle persistence failure
        println("Error saving distinct ID to $path: $e")
    }
}

internal suspend fun loadDistinctIdFromKotlinIO(): String? {
    val path = getDistinctIdPath() ?: return null

    // Check if the file exists before attempting to read
    if (!SystemFileSystem.exists(path) || SystemFileSystem.metadataOrNull(path)?.isRegularFile != true) {
        return null
    }

    return try {
        // Open the file for reading and read the entire content
        SystemFileSystem.source(path).use { source ->
            source.buffered().readText()
        }
    } catch (e: Exception) {
        // Handle cases like read permission issues or file corruption
        println("Error loading distinct ID from $path: $e")
        null
    }
}
