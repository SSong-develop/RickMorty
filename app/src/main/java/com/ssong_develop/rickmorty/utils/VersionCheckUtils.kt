package com.ssong_develop.rickmorty.utils

import android.os.Build

class VersionCheckUtils {
    fun checkIsMaterialVersion() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
}