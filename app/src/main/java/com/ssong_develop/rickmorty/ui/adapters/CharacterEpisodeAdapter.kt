package com.ssong_develop.rickmorty.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.core_model.Episode
import com.ssong_develop.rickmorty.R
import com.ssong_develop.rickmorty.databinding.ItemCharacterEpisodeBinding
import com.ssong_develop.rickmorty.ui.viewholders.CharacterEpisodeViewHolder

class CharacterEpisodeAdapter(
    private val delegate: CharacterEpisodeViewHolder.Delegate
) : RecyclerView.Adapter<CharacterEpisodeViewHolder>() {

    private val items: MutableList<Episode> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterEpisodeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemCharacterEpisodeBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_character_episode, parent, false)
        return CharacterEpisodeViewHolder(binding, delegate)
    }

    override fun onBindViewHolder(holder: CharacterEpisodeViewHolder, position: Int) {
        holder.bind(items[position])
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitEpisodes(episodes: List<Episode>) {
        items.clear()
        items.addAll(episodes)
        notifyDataSetChanged()
    }
}