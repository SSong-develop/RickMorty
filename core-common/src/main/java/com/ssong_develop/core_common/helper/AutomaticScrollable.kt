package com.ssong_develop.core_common.helper

import android.view.animation.Interpolator

interface AutomaticScrollable {
    val scrollAnimation: Interpolator

    fun advance()
}