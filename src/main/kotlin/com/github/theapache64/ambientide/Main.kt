package com.github.theapache64.ambientide

import com.intellij.openapi.application.PathManager
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import java.io.File

// For instant debugging (without assemble and load)
fun main(args: Array<String>) {
    App.args = args
    App().onCreate()
}

class MainComponent : StartupActivity, DumbAware {
    override fun runActivity(project: Project) {
        val logPath = "${PathManager.getLogPath()}${File.separator}idea.log"
        println("App started~!!! $logPath")
        App.args = arrayOf(logPath)
        App().onCreate()
    }
}