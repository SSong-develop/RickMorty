package com.ssong_develop.feature_character.presentation.detail.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.core_model.RickMortyCharacterEpisode
import com.ssong_develop.feature_character.R
import com.ssong_develop.feature_character.databinding.ItemCharacterEpisodeBinding
import com.ssong_develop.feature_character.presentation.detail.viewholders.CharacterEpisodeViewHolder
import com.ssong_develop.feature_character.presentation.detail.viewholders.OnEpisodeClickListener

internal class CharacterEpisodeAdapter(
    private val onEpisodeClickListener: OnEpisodeClickListener
) : RecyclerView.Adapter<CharacterEpisodeViewHolder>() {

    private val items: MutableList<RickMortyCharacterEpisode> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterEpisodeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemCharacterEpisodeBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_character_episode, parent, false)
        return CharacterEpisodeViewHolder(binding, onEpisodeClickListener)
    }

    override fun onBindViewHolder(holder: CharacterEpisodeViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitEpisodes(episodes: List<RickMortyCharacterEpisode>) {
        items.clear()
        items.addAll(episodes)
        notifyDataSetChanged()
    }
}