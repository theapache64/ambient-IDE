package com.github.theapache64.ambientide

import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// For instant debugging (without assemble and load)
fun main(@Suppress("UNUSED_PARAMETER") args: Array<String>) = runBlocking {
    App().onCreate()
}

@OptIn(DelicateCoroutinesApi::class)
class MainComponent : StartupActivity, DumbAware {
    init {
        GlobalScope.launch {
            println("Starting ambient-IDE")
            App().onCreate()
        }
    }

    override fun runActivity(project: Project) {
        // not interested
        println("starting ambient-IDE")
    }
}