package com.ssong_develop.feature_character.presentation.character.viewholders

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.feature_character.databinding.ItemLoadStateBinding

class LoadStateViewHolder(
    private val binding: ItemLoadStateBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(loadState: LoadState) {
        binding.pbLoading.isVisible = loadState is LoadState.Loading
    }
}