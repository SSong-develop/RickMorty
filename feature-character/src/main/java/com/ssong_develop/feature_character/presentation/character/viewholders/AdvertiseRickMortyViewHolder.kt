package com.ssong_develop.feature_character.presentation.character.viewholders

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ssong_develop.feature_character.databinding.ItemAdvertiseRickMortyBinding
import com.ssong_develop.feature_character.model.RickMortyAdvertiseUiModel

class AdvertiseRickMortyViewHolder(
    private val binding : ItemAdvertiseRickMortyBinding
) : ViewHolder(binding.root) {

    private lateinit var advertiseUiModel: RickMortyAdvertiseUiModel

    fun bind(data : RickMortyAdvertiseUiModel) {
        advertiseUiModel = data
        binding.apply {
            tvAdvertiseTitle.text = data.advertiseText
        }
    }
}