package com.ssong_develop.rickmorty.binding

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.ssong_develop.rickmorty.R

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("set_image_url")
    fun setImage(imageView : ImageView, imageUrl : String){
        Glide.with(imageView.context)
            .load(imageUrl)
            .into(imageView)
    }
}