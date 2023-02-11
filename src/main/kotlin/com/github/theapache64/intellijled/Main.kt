package com.github.theapache64.intellijled

import com.github.theapache64.intellijled.data.RulesRepoImpl
import com.github.theapache64.intellijled.data.WLEDRepoImpl
import com.github.theapache64.intellijled.model.IDE
import com.github.theapache64.wled.WLED
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import java.io.File

fun main(args: Array<String>) = runBlocking {
    val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    IntellijLED(
        logFile = File(args.first()),
        rulesRepo = RulesRepoImpl(json),
        ledRepo = WLEDRepoImpl(
            wled = WLED("wled.local")
        ),
        ide = IDE.AndroidStudio
    ).start()
}