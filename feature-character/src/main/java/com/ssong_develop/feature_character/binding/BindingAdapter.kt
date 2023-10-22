package com.ssong_develop.feature_character.binding

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.palette.graphics.Palette
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.card.MaterialCardView
import com.ssong_develop.core_common.extension.asBitmap
import com.ssong_develop.feature_character.model.Status
import com.ssong_develop.feature_character.presentation.character.UiState
import timber.log.Timber

object ViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("bind_dot_color")
    fun bindDotColor(view: TextView, status: String) {
        view.setTextColor(ContextCompat.getColor(view.context, Status.color(status)!!))
    }

    @JvmStatic
    @BindingAdapter("paletteImage", "paletteView")
    fun bindLoadImagePaletteView(view: ImageView, url: String?, paletteView: View) {
        val context = view.context
        Glide.with(context)
            .load(url)
            .listener(
                object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean = false

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        val palette = Palette.from(resource.asBitmap()).generate()
                        val dominantColor = palette.getDominantColor(Color.BLACK)
                        paletteView.setBackgroundColor(dominantColor)
                        if (context is AppCompatActivity) {
                            context.window.apply {
                                statusBarColor = dominantColor
                            }
                        }
                        return false
                    }
                }
            ).into(view)
    }
}

@BindingAdapter("bindProgressBarVisibility")
fun ProgressBar.bindProgressBarVisibility(state: UiState) {
    visibility = when (state) {
        UiState.Loading -> View.VISIBLE
        else -> View.GONE
    }
}

@BindingAdapter("bindErrorViewVisibility")
fun View.bindErrorViewVisibility(state: UiState) {
    visibility = when (state) {
        UiState.Error -> View.VISIBLE
        else -> View.GONE
    }
}

@BindingAdapter("bindSwipeRefreshVisibility")
fun SwipeRefreshLayout.bindSwipeRefreshVisibility(state: UiState) {
    visibility = when (state) {
        is UiState.Characters -> View.VISIBLE
        else -> View.GONE
    }
}