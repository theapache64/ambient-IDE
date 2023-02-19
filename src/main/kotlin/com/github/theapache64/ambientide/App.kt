package com.github.theapache64.intellijled

import com.github.theapache64.intellijled.ui.MainActivity
import com.theapache64.cyclone.core.Application
import com.theapache64.cyclone.core.Intent

class App : Application() {

    companion object{
        lateinit var args: Array<String>
    }
    override fun onCreate() {
        super.onCreate()
        startActivity(Intent(MainActivity::class))
    }
}