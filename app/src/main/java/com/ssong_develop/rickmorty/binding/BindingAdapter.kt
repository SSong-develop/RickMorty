package com.ssong_develop.rickmorty.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("set_image_url")
    fun setImageUrl(imageView: ImageView, url: String) {
        Glide.with(imageView)
            .load(url)
            .into(imageView)
    }
}