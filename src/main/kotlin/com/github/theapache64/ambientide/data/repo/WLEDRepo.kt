package com.github.theapache64.ambientide.data.repo

import com.github.theapache64.wled.WLED
import com.github.theapache64.wled.model.State

interface WLEDRepo {
    suspend fun updateSingleSegment(segment: State.Segment): Boolean
    suspend fun updateState(state: State): Boolean
    suspend fun power(boolean: Boolean): Boolean
}

class WLEDRepoImpl(
    private val wled: WLED
) : WLEDRepo {
    override suspend fun updateSingleSegment(segment: State.Segment): Boolean {
        println("Updating state...")
        return updateState(state = State(seg = listOf(segment)))
    }

    override suspend fun updateState(state: State): Boolean {
        return wled.updateState(state)
    }

    override suspend fun power(boolean: Boolean): Boolean {
        return wled.updateState(
            State(
                on = boolean
            )
        )
    }
}