package com.ssong_develop.rickmorty.utils

import android.app.Application
import androidx.annotation.Px
import javax.inject.Inject
import kotlin.math.roundToInt

class PixelRatio @Inject constructor(
    private val application: Application
) {
    private val displayMetrics = application.resources.displayMetrics

    val screenWidth = displayMetrics.widthPixels

    val screenHeight = displayMetrics.heightPixels

    @Px
    fun dpToPixel(dp: Int) = (dp * displayMetrics.density).roundToInt()
    fun dpToPixelFloat(dp: Float) = (dp * displayMetrics.density).toFloat()
}