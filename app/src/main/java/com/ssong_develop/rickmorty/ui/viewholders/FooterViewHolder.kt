package com.ssong_develop.rickmorty.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.rickmorty.databinding.ItemLoadingFooterBinding

class FooterViewHolder(
    private val binding : ItemLoadingFooterBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind() {
        binding.executePendingBindings()
    }
}