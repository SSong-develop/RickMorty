package com.ssong_develop.core_common

import android.content.Context
import android.content.Intent
import android.widget.Toast

fun Context.toast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

inline fun <reified T : Any> Context.getIntent() = Intent(this, T::class.java)
