plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.8.0"
    kotlin("plugin.serialization") version "1.8.0"
}

group = "com.github.theapache64"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")

    // Kotlinx Serialization JSON : Kotlin multiplatform serialization runtime library
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0-RC")

    // Retrofit : A type-safe HTTP client for Android and Java.
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // Retrofit 2 Kotlin Serialization Converter : A Converter.Factory for Kotlin's serialization support.
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}