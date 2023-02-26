package com.github.theapache64.ambientide

import com.github.theapache64.ambientide.ui.main.MainActivity
import com.theapache64.cyclone.core.Application
import com.theapache64.cyclone.core.Intent

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startActivity(Intent(MainActivity::class))
    }
}