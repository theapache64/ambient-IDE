package com.github.theapache64.ambientide.di

import com.github.theapache64.ambientide.di.module.CoreModule
import com.github.theapache64.ambientide.di.module.RepoModule
import com.github.theapache64.ambientide.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RepoModule::class,
        CoreModule::class,
    ]
)
interface AppComponent {
    fun inject(mainActivity : MainActivity)
}