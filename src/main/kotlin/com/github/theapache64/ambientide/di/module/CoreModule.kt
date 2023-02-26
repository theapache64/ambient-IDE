package com.github.theapache64.ambientide.di.module

import com.github.theapache64.ambientide.model.IDE
import com.github.theapache64.wled.WLED
import com.intellij.openapi.application.PathManager
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import java.io.File
import javax.inject.Named
import javax.inject.Singleton

@Module
class CoreModule {
    companion object {
        const val namedLogFile = "log_file"
    }

    @Provides
    fun provideIde(@Named(namedLogFile) logFile: File): IDE {
        return if (logFile.parentFile.name.startsWith("AndroidStudio")) {
            IDE.AndroidStudio
        } else {
            IDE.IntelliJ
        }
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
    fun provideLogFile(): File {
        return File("${PathManager.getLogPath()}${File.separator}idea.log")
    }

    @Singleton
    @Provides
    fun provideWled(): WLED {
        return WLED("wled.local") // TODO: Make it dynamic
    }
}