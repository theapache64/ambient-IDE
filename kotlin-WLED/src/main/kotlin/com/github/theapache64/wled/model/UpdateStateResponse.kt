package com.github.theapache64.wled.model
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class UpdateStateResponse(
    @SerialName("success")
    val success: Boolean
)