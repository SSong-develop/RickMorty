package com.ssong_develop.rickmorty.binding

import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.google.android.material.card.MaterialCardView
import com.ssong_develop.rickmorty.extensions.toast

object ViewBinding {

    @JvmStatic
    @BindingAdapter("toast")
    fun bindToast(view : View, message : String?){
        message?.let { view.context.toast(it) }
    }


    @JvmStatic
    @BindingAdapter("set_image_url")
    fun setImageUrl(imageView: AppCompatImageView, url: String) {
        Glide.with(imageView)
            .load(url)
            .into(imageView)
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
}