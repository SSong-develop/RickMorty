package com.ssong_develop.rickmorty.rickmorty

import app.cash.turbine.test
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class FlowTest {

    @Test
    fun testFlow() = runBlocking {
        flowOf("one", "two").test {
            assertEquals("one", awaitItem())
            assertEquals("two", awaitItem())
            awaitComplete()
        }
    }
}