package com.github.theapache64.ambientide

import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import kotlinx.coroutines.runBlocking

// CLI Main
fun main(args: Array<String>) = runBlocking {
    App.args = args
    App().onCreate()
}

class MainComponent : StartupActivity, DumbAware {

    override fun runActivity(project: Project) {
        println("App started~!!! $project")
        App.args = arrayOf(
            "/Users/theapache64/Library/Logs/Google/AndroidStudio2021.3/idea.log"
        )
        App().onCreate()
    }

}