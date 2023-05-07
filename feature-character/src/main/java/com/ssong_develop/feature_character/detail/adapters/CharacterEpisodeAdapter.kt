package com.ssong_develop.feature_character.detail.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.core_model.RickMortyCharacterEpisode
import com.ssong_develop.feature_character.R
import com.ssong_develop.feature_character.databinding.ItemCharacterEpisodeBinding
import com.ssong_develop.feature_character.detail.viewholders.CharacterEpisodeViewHolder

class CharacterEpisodeAdapter(
    private val delegate: CharacterEpisodeViewHolder.Delegate
) : RecyclerView.Adapter<CharacterEpisodeViewHolder>() {

    private val items: MutableList<RickMortyCharacterEpisode> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterEpisodeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemCharacterEpisodeBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_character_episode, parent, false)
        return CharacterEpisodeViewHolder(
            binding,
            delegate
        )
    }

    override fun onBindViewHolder(
        holder: CharacterEpisodeViewHolder,
        position: Int
    ) {
        holder.bind(items[position])
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitEpisodes(episodes: List<RickMortyCharacterEpisode>) {
        Log.d("ssong-develop3","${episodes}")
        items.clear()
        items.addAll(episodes)
        notifyDataSetChanged()
    }
}