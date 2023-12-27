package com.ssong_develop.feature_character.binding

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ssong_develop.feature_character.model.Status
import com.ssong_develop.feature_character.presentation.character.UiState

object ViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("bind_dot_color")
    fun bindDotColor(view: TextView, status: String) {
        view.setTextColor(ContextCompat.getColor(view.context, Status.color(status)!!))
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