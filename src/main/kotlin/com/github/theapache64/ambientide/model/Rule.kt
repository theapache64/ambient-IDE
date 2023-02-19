package com.github.theapache64.intellijled.model

import com.github.theapache64.wled.model.State
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object RegexAsStringSerializer : KSerializer<Regex> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Regex", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: Regex) = encoder.encodeString(value.pattern)
    override fun deserialize(decoder: Decoder): Regex = decoder.decodeString().toRegex()
}

@Serializable
data class Rule(
    @SerialName("regex")
    @Serializable(with = RegexAsStringSerializer::class)
    val regex: Regex,
    @SerialName("segment")
    val segment: State.Segment
)

