package com.ssong_develop.feature_character.presentation.detail.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.core_model.RickMortyCharacterEpisode
import com.ssong_develop.feature_character.databinding.ItemCharacterEpisodeBinding

class CharacterEpisodeViewHolder(
    val binding: ItemCharacterEpisodeBinding,
    private val delegate: Delegate
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

    private lateinit var episode: RickMortyCharacterEpisode

    init {
        binding.root.setOnClickListener(this)
        binding.root.setOnLongClickListener(this)
    }

    interface Delegate {
        fun onItemClick(view: View, episode: RickMortyCharacterEpisode)
    }

    fun bind(episode: RickMortyCharacterEpisode) {
        this.episode = episode
        binding.apply {
            this.episode = episode
            executePendingBindings()
        }
    }

    override fun onClick(view: View) {
        delegate.onItemClick(view, episode)
    }

    override fun onLongClick(v: View?): Boolean = false
}