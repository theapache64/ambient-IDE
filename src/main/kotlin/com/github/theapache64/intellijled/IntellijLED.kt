package com.github.theapache64.intellijled

import com.github.theapache64.fastfilewatcher.FastFileWatcher
import com.github.theapache64.intellijled.data.RulesRepo
import com.github.theapache64.intellijled.data.WLEDRepo
import com.github.theapache64.intellijled.model.IDE
import java.io.File

class IntellijLED(
    val logFile: File,
    rulesRepo: RulesRepo,
    private val ledRepo: WLEDRepo,
    private val ide : IDE
) {
    private val rules = rulesRepo.parseRules(ide)

    suspend fun start() {
        println("Initializing intellij-LED...")

        val fileWatcher = FastFileWatcher(
            file = logFile,
            onFileUpdated = { lines ->
                for (line in lines) {
                    process(line)
                }
            }
        )

        fileWatcher.start()
    }

    private suspend fun process(line: String) {
        for (rule in rules) {
            if (rule.regex.matches(line)) {
                ledRepo.updateState(rule.segment).also {
                    println("WLED State updated : $it ")
                }
                break
            }
        }
    }
}