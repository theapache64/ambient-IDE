package com.github.theapache64.intellijled

import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) = runBlocking {
    App.args = args
    App().onCreate()
}