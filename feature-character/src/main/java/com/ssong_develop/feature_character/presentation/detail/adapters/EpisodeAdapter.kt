package com.ssong_develop.feature_character.presentation.detail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ssong_develop.core_model.RickMortyCharacterEpisode
import com.ssong_develop.feature_character.R
import com.ssong_develop.feature_character.databinding.ItemCharacterEpisodeBinding
import com.ssong_develop.feature_character.model.RickMortyCharacterUiModel
import com.ssong_develop.feature_character.presentation.detail.viewholders.CharacterEpisodeViewHolder
import com.ssong_develop.feature_character.presentation.detail.viewholders.OnEpisodeClickListener

private val diffUtil = object : DiffUtil.ItemCallback<RickMortyCharacterEpisode>() {
    override fun areItemsTheSame(
        oldItem: RickMortyCharacterEpisode,
        newItem: RickMortyCharacterEpisode
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: RickMortyCharacterEpisode,
        newItem: RickMortyCharacterEpisode
    ): Boolean = oldItem == newItem
}

internal class EpisodeAdapter(
    private val onEpisodeClickListener: OnEpisodeClickListener
) : ListAdapter<RickMortyCharacterEpisode, CharacterEpisodeViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterEpisodeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemCharacterEpisodeBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_character_episode, parent, false)
        return CharacterEpisodeViewHolder(binding, onEpisodeClickListener)
    }

    override fun onBindViewHolder(holder: CharacterEpisodeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}