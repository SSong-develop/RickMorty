package com.ssong_develop.rickmorty.binding

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ssong_develop.rickmorty.ui.character.CharacterViewModel

object SwipeRefreshViewBinding {
    @JvmStatic
    @BindingAdapter("refresh")
    fun refresh(view: SwipeRefreshLayout, viewModel: CharacterViewModel) {
        view.setOnRefreshListener {
            viewModel.refreshPage()
            view.isRefreshing = false
        }
    }
}