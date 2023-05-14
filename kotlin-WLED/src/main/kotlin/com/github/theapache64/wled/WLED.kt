package com.github.theapache64.wled

import com.github.theapache64.wled.model.State
import com.github.theapache64.wled.model.UpdateStateResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

private interface WLEDApi {
    @POST("/json/state")
    suspend fun updateState(
        @Body state: State
    ): UpdateStateResponse

}

class WLED(
    val domain: String
) {
    @OptIn(ExperimentalSerializationApi::class)
    private val wledApi by lazy {
        val json = Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
        }


        Retrofit.Builder()
            .baseUrl("http://$domain")
            .addConverterFactory(json.asConverterFactory(MediaType.parse("application/json")!!))
            .build()
            .create(WLEDApi::class.java)
    }

    suspend fun updateState(newState: State): Boolean {
        return wledApi.updateState(newState).success
    }
}