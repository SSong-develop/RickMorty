package com.ssong_develop.feature_character.presentation.character.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ssong_develop.feature_character.databinding.ItemAdvertiseRickMortyBinding
import com.ssong_develop.feature_character.model.RickMortyAdvertiseUiModel
import com.ssong_develop.feature_character.presentation.character.viewholders.AdvertiseRickMortyViewHolder

private val advertiseRickMortyDiffUtil = object : DiffUtil.ItemCallback<RickMortyAdvertiseUiModel>() {
    override fun areItemsTheSame(
        oldItem: RickMortyAdvertiseUiModel,
        newItem: RickMortyAdvertiseUiModel
    ): Boolean = oldItem.advertiseId == newItem.advertiseId

    override fun areContentsTheSame(
        oldItem: RickMortyAdvertiseUiModel,
        newItem: RickMortyAdvertiseUiModel
    ): Boolean = oldItem == newItem
}

class AdvertiseRickMortyAdapter : ListAdapter<RickMortyAdvertiseUiModel, AdvertiseRickMortyViewHolder>(advertiseRickMortyDiffUtil){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvertiseRickMortyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemAdvertiseRickMortyBinding.inflate(layoutInflater, parent, false)
        return AdvertiseRickMortyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdvertiseRickMortyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
