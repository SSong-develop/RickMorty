package com.ssong_develop.feature_character.presentation.character.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.feature_character.databinding.ItemLoadingFooterBinding

internal class FooterViewHolder(
    private val binding: ItemLoadingFooterBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind() {
        binding.executePendingBindings()
    }
}