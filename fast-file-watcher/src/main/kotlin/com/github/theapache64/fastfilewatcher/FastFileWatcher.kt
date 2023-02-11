package com.github.theapache64.fastfilewatcher

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File

class FastFileWatcher(
    private val file: File,
    private val onFileUpdated: suspend (addedLines: List<String>) -> Unit,
    private val pollDelay: Long = 300,
) {
    private var isRunning = true
    suspend fun start() = withContext(Dispatchers.IO) {
        isRunning = true
        println("Observing for content change in '${file.absolutePath}' (${pollDelay}ms)")
        var prevFileModified = file.lastModified()
        var prevFileLinesCount = file.readLines().size
        while (isRunning) {
            delay(pollDelay)
            val newFileModified = file.lastModified()
            if (prevFileModified != newFileModified) {
                // file modified
                val newLines = file.readLines(startLine = prevFileLinesCount)
                onFileUpdated(newLines)

                // Updating prev flags
                prevFileLinesCount += newLines.size
                prevFileModified = newFileModified
            }
        }
    }

    fun stop() {
        isRunning = false
    }

    private fun File.readLines(startLine: Int): List<String> {
        val lineSequence = bufferedReader().lineSequence()
        return lineSequence.drop(startLine - 1).toList()
    }

}