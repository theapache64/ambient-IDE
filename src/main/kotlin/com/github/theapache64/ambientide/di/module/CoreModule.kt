package com.github.theapache64.ambientide.di.module

import com.github.theapache64.ambientide.model.IDE
import com.github.theapache64.wled.WLED
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import java.io.File
import javax.inject.Named
import javax.inject.Singleton


@Module
class CoreModule(
    private val ide: IDE
) {
    companion object {
        const val namedLogFile = "log_file"
    }

    @Provides
    fun provideIde(): IDE {
        return ide
    }

    @Singleton
    @Provides
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
        }
    }

    @Singleton
    @Provides
    @Named(namedLogFile)
    fun provideLogFile(
        ide: IDE
    ): File {
        return File("/Users/theapache64/Library/Logs/Google/AndroidStudio2021.3/idea.log")
    }

    @Singleton
    @Provides
    fun provideWled(): WLED {
        return WLED("wled.local")
    }
}