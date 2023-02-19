package com.github.theapache64.intellijled.ui

import com.github.theapache64.intellijled.App.Companion.args
import com.github.theapache64.intellijled.IntellijLED
import com.github.theapache64.intellijled.data.repo.RulesRepoImpl
import com.github.theapache64.intellijled.data.repo.WLEDRepoImpl
import com.github.theapache64.intellijled.model.IDE
import com.github.theapache64.wled.WLED
import com.theapache64.cyclone.core.Activity
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import java.io.File

class MainActivity : Activity() {
    override fun onCreate() {
        super.onCreate()

        val json = Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
        }

        runBlocking {
            IntellijLED(
                logFile = File(args.first()),
                rulesRepo = RulesRepoImpl(json),
                ledRepo = WLEDRepoImpl(
                    wled = WLED("wled.local")
                ),
                ide = IDE.AndroidStudio
            ).start()
        }
    }
}