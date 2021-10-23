package com.ssong_develop.rickmorty.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ssong_develop.rickmorty.R
import com.ssong_develop.rickmorty.databinding.ItemCharacterEpisodeBinding
import com.ssong_develop.rickmorty.entities.Episode
import com.ssong_develop.rickmorty.ui.viewholders.CharacterEpisodeListViewHolder

private val characterEpisodeDiffItemCallback = object : DiffUtil.ItemCallback<Episode>() {
    override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean =
        oldItem.id == newItem.id
}

class CharacterEpisodeListAdapter(
    private val delegate: CharacterEpisodeListViewHolder.Delegate
) : ListAdapter<Episode, CharacterEpisodeListViewHolder>(characterEpisodeDiffItemCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterEpisodeListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemCharacterEpisodeBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_character_episode, parent, false)
        return CharacterEpisodeListViewHolder(binding, delegate)
    }

    override fun onBindViewHolder(holder: CharacterEpisodeListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}