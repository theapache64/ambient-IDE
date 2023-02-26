package com.github.theapache64.ambientide.ui.main

import com.github.theapache64.ambientide.di.DaggerAppComponent
import com.github.theapache64.ambientide.di.module.CoreModule
import com.github.theapache64.ambientide.model.IDE
import com.theapache64.cyclone.core.Activity
import javax.inject.Inject

class MainActivity : Activity() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .build()
            .inject(this)
        mainViewModel.init()
    }
}