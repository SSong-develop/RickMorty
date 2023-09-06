package com.ssong_develop.rickmorty

import android.content.Context
import android.os.StrictMode
import androidx.startup.Initializer

class StrictModeInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build()
        )
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}