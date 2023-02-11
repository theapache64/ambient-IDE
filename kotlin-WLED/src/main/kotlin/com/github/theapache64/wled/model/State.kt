package com.github.theapache64.wled.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class State(
    @SerialName("bri")
    val bri: Int? = null,
    @SerialName("lor")
    val lor: Int? = null,
    @SerialName("mainseg")
    val mainseg: Int? = null,
    @SerialName("nl")
    val nl: Nl? = null,
    @SerialName("on")
    val on: Boolean? = null,
    @SerialName("pl")
    val pl: Int? = null,
    @SerialName("ps")
    val ps: Int? = null,
    @SerialName("seg")
    val seg: List<Segment?>? = null,
    @SerialName("transition")
    val transition: Int? = null,
    @SerialName("udpn")
    val udpn: Udpn? = null
) {
    @Serializable
    data class Nl(
        @SerialName("dur")
        val dur: Int? = null,
        @SerialName("mode")
        val mode: Int? = null,
        @SerialName("on")
        val on: Boolean? = null,
        @SerialName("rem")
        val rem: Int? = null,
        @SerialName("tbri")
        val tbri: Int? = null
    )


    @Serializable
    data class Segment(
        @SerialName("bri")
        val bri: Int? = null,
        @SerialName("cct")
        val cct: Int? = null,
        @SerialName("col")
        val col: List<List<Int?>?>? = null,
        @SerialName("frz")
        val frz: Boolean? = null,
        @SerialName("fx")
        val fx: Int? = null,
        @SerialName("grp")
        val grp: Int? = null,
        @SerialName("id")
        val id: Int? = null,
        @SerialName("ix")
        val ix: Int? = null,
        @SerialName("len")
        val len: Int? = null,
        @SerialName("mi")
        val mi: Boolean? = null,
        @SerialName("of")
        val of: Int? = null,
        @SerialName("on")
        val on: Boolean? = null,
        @SerialName("pal")
        val pal: Int? = null,
        @SerialName("rev")
        val rev: Boolean? = null,
        @SerialName("sel")
        val sel: Boolean? = null,
        @SerialName("spc")
        val spc: Int? = null,
        @SerialName("start")
        val start: Int? = null,
        @SerialName("stop")
        val stop: Int? = null,
        @SerialName("sx")
        val sx: Int?
    )

    @Serializable
    data class Udpn(
        @SerialName("recv")
        val recv: Boolean? = null,
        @SerialName("send")
        val send: Boolean? = null
    )
}