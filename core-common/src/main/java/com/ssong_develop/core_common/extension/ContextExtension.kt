package com.ssong_develop.core_common.extension

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

/** dp size to pixel size **/
fun Context.dpToPx(dp: Int): Int {
    val scale = resources.displayMetrics.density
    return (dp * scale).toInt()
}

/** dp Float size to pixel size **/
fun Context.dpToPx(dp: Float): Int {
    val scale = resources.displayMetrics.density
    return (dp * scale).toInt()
}

/** get drawable, if null then throw IllegalArgumentException **/
fun Context.getDrawableOrThrow(@DrawableRes resourceId: Int): Drawable {
    return ContextCompat.getDrawable(this, resourceId)
        ?: throw IllegalArgumentException("Not exist resource id")
}

/** get Drawable by DrawableResId **/
fun Context.getDrawableOrNull(@DrawableRes resourceId: Int): Drawable? {
    return ContextCompat.getDrawable(this, resourceId)
}

/** short duration toast message **/
fun Context.shortToast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

/** long duration toast message **/
fun Context.longToast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()

/** get Intent with generic type **/
inline fun <reified T : Any> Context.getIntent() =
    Intent(this, T::class.java)

/** get Intent with Lamda **/
inline fun <reified T : Any> Context.getIntent(block: () -> Unit) =
    Intent(this, T::class.java).apply { block() }

/** check install app(@param packageName) **/
fun Context.isInstalledApp(packageName: String): Boolean {
    val intent = packageManager.getLaunchIntentForPackage(packageName)
    return intent != null
}
