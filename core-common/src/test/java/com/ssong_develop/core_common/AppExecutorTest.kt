package com.ssong_develop.core_common

import android.util.Log
import kotlinx.coroutines.DelicateCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.concurrent.Executors

@DelicateCoroutinesApi
class AppExecutorTest {

    lateinit var appExecutors: AppExecutors

    // Hilt Mocking 데이터가 있어야 테스트 가능함
    lateinit var injectAppExecutors: AppExecutors

    @Before
    fun setUp() {
        appExecutors = AppExecutors(
            diskIO = Executors.newSingleThreadExecutor { runnable ->
                Thread(runnable, "disk_io_thread")
            },
            networkIO = Executors.newSingleThreadExecutor { runnable ->
                Thread(runnable, "network_io_thread")
            },
            mainThread = Executors.newSingleThreadExecutor { runnable ->
                Thread(runnable, "main_thread")
            }
        )
        injectAppExecutors = AppExecutors()
    }

    @Test
    fun `쓰레드 생성 테스트`() {
        appExecutors.diskIO().execute {
            assertEquals("disk_io_thread", Thread.currentThread().name)
        }
        appExecutors.networkIO().execute {
            assertEquals("network_io_thread", Thread.currentThread().name)
        }
        appExecutors.mainThread().execute {
            assertEquals("main_thread", Thread.currentThread().name)
        }
    }

    @Test
    fun `생성자 주입 쓰레드 테스트`() {
        injectAppExecutors.networkIO().execute {
            Log.d("ssong-develop", Thread.currentThread().name)
        }
    }

}