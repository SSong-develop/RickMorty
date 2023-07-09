package com.ssong_develop.core_common.extension

import android.os.Parcel
import androidx.core.os.ParcelCompat

fun Parcel.writeBooleanUsingCompat(value: Boolean) = ParcelCompat.writeBoolean(this, value)

fun Parcel.readBooleanUsingCompat() = ParcelCompat.readBoolean(this)
