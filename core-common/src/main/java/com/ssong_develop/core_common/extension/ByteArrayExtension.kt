package com.ssong_develop.core_common.extension

import android.graphics.Bitmap
import android.graphics.BitmapFactory

fun ByteArray.asBitmap(): Bitmap = this.takeIf { it.isNotEmpty() }
    ?.let { byteArray ->
        BitmapFactory.decodeByteArray(byteArray,0, byteArray.size)
    } ?: throw IllegalStateException("ByteArray size is zero")