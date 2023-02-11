package com.github.theapache64.intellijled.data

import com.github.theapache64.wled.WLED
import com.github.theapache64.wled.model.State

interface WLEDRepo {
    suspend fun updateState(segment: State.Segment) : Boolean
}

class WLEDRepoImpl(
    private val wled : WLED
) : WLEDRepo {
    override suspend fun updateState(segment: State.Segment) : Boolean {
        return wled.updateState(
            State(
                seg = listOf(segment)
            )
        )
    }
}