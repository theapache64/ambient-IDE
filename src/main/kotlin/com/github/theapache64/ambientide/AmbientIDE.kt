package com.github.theapache64.ambientide

import com.github.theapache64.ambientide.data.repo.RulesRepo
import com.github.theapache64.ambientide.data.repo.WLEDRepo
import com.github.theapache64.ambientide.di.module.CoreModule
import com.github.theapache64.ambientide.model.IDE
import com.github.theapache64.fastfilewatcher.FastFileWatcher
import java.io.File
import javax.inject.Inject
import javax.inject.Named

class AmbientIDE @Inject constructor(
    @Named(CoreModule.namedLogFile) private val logFile: File,
    rulesRepo: RulesRepo,
    private val ledRepo: WLEDRepo,
    ide: IDE,
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
                ledRepo.updateSingleSegment(rule.segment).also {
                    println("WLED State updated : isUpdated=`$it`")
                }
                break
            }
        }
    }
}