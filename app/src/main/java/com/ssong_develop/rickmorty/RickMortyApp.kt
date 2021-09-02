package com.ssong_develop.rickmorty

import android.app.Application
import androidx.viewbinding.BuildConfig
import com.ssong_develop.rickmorty.utils.VersionCheckUtils
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class RickMortyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeSingleton()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initializeSingleton() {
        versionCheckUtils = VersionCheckUtils()
    }

    companion object {
        lateinit var versionCheckUtils : VersionCheckUtils
    }
}