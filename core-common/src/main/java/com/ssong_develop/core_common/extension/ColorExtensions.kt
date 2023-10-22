package com.ssong_develop.core_common.extension

import android.graphics.Color

fun Color.toInvertedColor() = Color.rgb(
    255 - this.red(),
    255 - this.green(),
    255 - this.blue()
)