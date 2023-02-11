package com.github.theapache64.intellijled.data.repo

import com.github.theapache64.wled.WLED
import com.github.theapache64.wled.model.State

interface WLEDRepo {
    suspend fun updateSingleSegment(segment: State.Segment) : Boolean
    suspend fun power(boolean: Boolean) : Boolean
}

class WLEDRepoImpl(
    private val wled : WLED
) : WLEDRepo {
    override suspend fun updateSingleSegment(segment: State.Segment) : Boolean {
        println("Updating state...")
        return wled.updateState(
            State(
                seg = listOf(segment)
            )
        )
    }

    override suspend fun power(boolean: Boolean): Boolean {
        return wled.updateState(
            State(
                on = boolean
            )
        )
    }
}