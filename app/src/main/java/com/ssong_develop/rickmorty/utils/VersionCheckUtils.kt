package com.ssong_develop.rickmorty.utils

import android.os.Build
import com.ssong_develop.rickmorty.BuildConfig.VERSION_CODE

class VersionCheckUtils {
    fun checkIsMaterialVersion() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
}