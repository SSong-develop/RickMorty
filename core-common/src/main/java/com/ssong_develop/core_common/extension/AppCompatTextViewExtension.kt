package com.ssong_develop.core_common.extension

import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.TextViewCompat

fun AppCompatTextView.adjustTextSizeSp(
    minSize: Int,
    maxSize: Int,
    step: Int
) {
    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
        this,
        minSize,
        maxSize,
        step,
        TypedValue.COMPLEX_UNIT_SP
    )
}

fun AppCompatTextView.adjustTextSizeDp(
    minSize: Int,
    maxSize: Int,
    step: Int
) {
    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
        this,
        minSize,
        maxSize,
        step,
        TypedValue.COMPLEX_UNIT_DIP
    )
}