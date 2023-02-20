package com.github.theapache64.ambientide.ui.main

import com.github.theapache64.ambientide.AmbientIDE
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val ambientIDE: AmbientIDE
) {
    fun init() {
        runBlocking {
            ambientIDE.start()
        }
    }
}