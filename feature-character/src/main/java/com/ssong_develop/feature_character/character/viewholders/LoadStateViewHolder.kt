package com.ssong_develop.feature_character.character.viewholders

import android.view.View
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.feature_character.databinding.ItemLoadStateBinding

class LoadStateViewHolder(
    private val binding: ItemLoadStateBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(loadState: LoadState) {
        binding.test.isVisible = loadState is LoadState.Loading

    }
}