package com.github.theapache64.ambientide.model

enum class IDE(
    val defaultConfig : String,
    val configName : String
) {
    AndroidStudio(defaultConfig = "default_android_studio_rules.json", "android_studio_rules.json"),
    IntelliJ("default_intellij_idea_rules.json", "intellij_idea_rules.json")
}