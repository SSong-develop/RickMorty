package com.ssong_develop.core_common.extension

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable

fun Drawable.asBitmap(): Bitmap {
    val bitmap = Bitmap.createBitmap(
        intrinsicWidth,
        intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    this.apply {
        setBounds(
            0,
            0,
            intrinsicWidth,
            intrinsicHeight
        )
        draw(canvas)
    }
    return bitmap
}