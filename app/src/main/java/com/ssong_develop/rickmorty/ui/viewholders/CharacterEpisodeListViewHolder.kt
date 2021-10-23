package com.ssong_develop.rickmorty.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.rickmorty.databinding.ItemCharacterEpisodeBinding
import com.ssong_develop.rickmorty.entities.Episode

class CharacterEpisodeListViewHolder(
    private val binding : ItemCharacterEpisodeBinding,
    private val delegate : Delegate
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

    private lateinit var episode : Episode

    init {
        binding.root.setOnClickListener(this)
        binding.root.setOnLongClickListener(this)
    }

    interface Delegate {
        fun onItemClick(view : View, episode : Episode)
    }

    fun bind(_episode : Episode){
        episode = _episode
        binding.apply {
            episode = episode
            executePendingBindings()
        }
    }

    override fun onClick(view: View) {
        delegate.onItemClick(view,episode)
    }

    override fun onLongClick(v: View?): Boolean = false
}