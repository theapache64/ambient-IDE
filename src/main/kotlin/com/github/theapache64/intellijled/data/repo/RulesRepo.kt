package com.github.theapache64.intellijled.data.repo

import com.github.theapache64.intellijled.model.IDE
import com.github.theapache64.intellijled.model.Rule
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

interface RulesRepo {
    fun parseRules(ide: IDE): List<Rule>
}

class RulesRepoImpl(
    val json: Json
) : RulesRepo {

    @OptIn(ExperimentalTime::class)
    override fun parseRules(ide: IDE): List<Rule> {
        val (value, duration) = measureTimedValue<List<Rule>> {
            val configFile = getToolHome().resolve(ide.configName)
            if (!configFile.exists()) {
                val configStream = ClassLoader.getSystemClassLoader().getResourceAsStream(ide.defaultConfig) ?: error("Couldn't read ${ide.defaultConfig}")
                val text = configStream.bufferedReader().readText()
                println("Writing default config...")
                configFile.writeText(text)
            }

            json.decodeFromString(configFile.readText())
        }
        println("Time took to parse rules : ${duration.inWholeMilliseconds}ms")
        return value
    }


    private fun getToolHome(): File {
        val toolHome =  System.getProperty("user.home") + File.separator + ".config" + File.separator + "intellij-led"
        return File(toolHome).also {
            if(!it.exists()){
                it.mkdirs()
            }
        }
    }
}

