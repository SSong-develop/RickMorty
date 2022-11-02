package com.ssong_develop.feature_character.detail.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.core_model.Episode
import com.ssong_develop.feature_character.databinding.ItemCharacterEpisodeBinding

class CharacterEpisodeViewHolder(
    val binding: ItemCharacterEpisodeBinding,
    private val delegate: Delegate
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

    private lateinit var episode: Episode

    init {
        binding.root.setOnClickListener(this)
        binding.root.setOnLongClickListener(this)
    }

    interface Delegate {
        fun onItemClick(view: View, episode: Episode)
    }

    fun bind(_episode: Episode) {
        episode = _episode
        binding.apply {
            episode = _episode
            executePendingBindings()
        }
    }

    override fun onClick(view: View) {
        delegate.onItemClick(view, episode)
    }

    override fun onLongClick(v: View?): Boolean = false
}