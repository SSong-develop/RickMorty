package com.ssong_develop.core_common.extension

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

fun Bitmap.asByteArray(): ByteArray {
    val byteArrayOutputStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    return byteArrayOutputStream.toByteArray()
}
