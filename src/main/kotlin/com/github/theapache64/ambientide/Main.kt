package com.github.theapache64.ambientide

import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity

// For instant debugging (without assemble and load)
fun main(@Suppress("UNUSED_PARAMETER") args: Array<String>) {
    App().onCreate()
}

class MainComponent : StartupActivity, DumbAware {
    override fun runActivity(project: Project) {
        println("💡ambient-IDE started")
        App().onCreate()
    }
}