package com.ssong_develop.feature_character.binding

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.google.android.material.card.MaterialCardView
import com.ssong_develop.feature_character.model.Status
import com.ssong_develop.feature_character.presentation.character.CharacterUiState

object ViewBinding {

    @JvmStatic
    @BindingAdapter("bind_dot_color")
    fun bindDotColor(view: TextView, status: String) {
        view.setTextColor(ContextCompat.getColor(view.context, Status.color(status)!!))
    }

    @JvmStatic
    @BindingAdapter("paletteImage", "paletteCard")
    fun bindLoadImagePalette(view: AppCompatImageView, url: String, paletteCard: MaterialCardView) {
        Glide.with(view.context)
            .load(url)
            .listener(
                GlidePalette.with(url)
                    .use(BitmapPalette.Profile.MUTED_LIGHT)
                    .intoCallBack { palette ->
                        val rgb = palette?.dominantSwatch?.rgb
                        if (rgb != null) {
                            paletteCard.setCardBackgroundColor(rgb)
                        }
                    }.crossfade(true)
            ).into(view)
    }

    @JvmStatic
    @BindingAdapter("paletteImage", "paletteView")
    fun bindLoadImagePaletteView(view: AppCompatImageView, url: String?, paletteView: View) {
        val context = view.context
        Glide.with(context)
            .load(url)
            .listener(
                GlidePalette.with(url)
                    .use(BitmapPalette.Profile.MUTED_LIGHT)
                    .intoCallBack { palette ->
                        val rgb = palette?.dominantSwatch?.rgb
                        if (rgb != null) {
                            paletteView.setBackgroundColor(rgb)
                            if (context is AppCompatActivity) {
                                context.window.apply {
                                    statusBarColor = rgb
                                }
                            }
                        }
                    }.crossfade(true)
            ).into(view)
    }

    @JvmStatic
    @BindingAdapter("bindSwipeRefreshLayout")
    fun bindSwipeRefreshLayout(view: SwipeRefreshLayout, block: () -> Unit) {
        view.setOnRefreshListener {
            block()
            view.isRefreshing = false
        }
    }
}

@BindingAdapter("bindProgressBarVisibility")
fun ProgressBar.bindProgressBarVisibility(state: CharacterUiState) {
    visibility = when (state) {
        CharacterUiState.Loading -> View.VISIBLE
        else -> View.GONE
    }
}

@BindingAdapter("bindErrorViewVisibility")
fun View.bindErrorViewVisibility(state: CharacterUiState) {
    visibility = when (state) {
        CharacterUiState.Error -> View.VISIBLE
        else -> View.GONE
    }
}

@BindingAdapter("bindSwipeRefreshVisibility")
fun SwipeRefreshLayout.bindSwipeRefreshVisibility(state: CharacterUiState) {
    visibility = when (state) {
        is CharacterUiState.Characters -> View.VISIBLE
        else -> View.GONE
    }
}