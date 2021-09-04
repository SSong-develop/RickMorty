package com.ssong_develop.rickmorty.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.rickmorty.databinding.ItemEpisodeBinding
import com.ssong_develop.rickmorty.entities.Episode

class EpisodeListViewHolder(
    private val binding: ItemEpisodeBinding,
    private val delegate: Delegate
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

    lateinit var episode: Episode

    init {
        binding.root.setOnClickListener(this)
        binding.root.setOnLongClickListener(this)
    }

    interface Delegate {
        fun onItemClick(view: View, episode: Episode)
    }

    fun bind(data: Episode) {
        episode = data
        binding.apply {
            episode = data
            executePendingBindings()
        }
    }

    override fun onClick(view: View) {
        delegate.onItemClick(view, episode)
    }

    override fun onLongClick(v: View?): Boolean = false
}