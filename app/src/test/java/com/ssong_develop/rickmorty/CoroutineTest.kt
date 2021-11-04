package com.ssong_develop.rickmorty

import junit.framework.Assert.assertEquals
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class CoroutineTest {

    // Thread.currentThread().name will be UI thread but, name is changed like UI thread [ @Coroutine#2]
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp(){
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun testSomeUI(): Unit = runBlocking {
        launch(Dispatchers.Main) { // Will be launched in the mainThreadSurrogate dispatcher
            assertEquals(Thread.currentThread().name , "UI thread @coroutine#2")
        }
    }

}