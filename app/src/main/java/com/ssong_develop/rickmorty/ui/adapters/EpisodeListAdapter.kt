package com.ssong_develop.rickmorty.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.rickmorty.R
import com.ssong_develop.rickmorty.databinding.ItemEpisodeBinding
import com.ssong_develop.rickmorty.entities.Episode
import com.ssong_develop.rickmorty.ui.viewholders.EpisodeListViewHolder

private val episodeItemDiffItemCallback = object : DiffUtil.ItemCallback<Episode>() {
    override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean =
        oldItem.id == newItem.id
}

class EpisodeListAdapter(
    private val delegate: EpisodeListViewHolder.Delegate
) : ListAdapter<Episode, EpisodeListViewHolder>(episodeItemDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemEpisodeBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_episode, parent, false)
        return EpisodeListViewHolder(binding, delegate)
    }

    override fun onBindViewHolder(holder: EpisodeListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

@BindingAdapter("episode_item")
fun RecyclerView.setEpisodeItem(list: List<Episode>?) {
    (adapter as EpisodeListAdapter)?.run {
        submitList(list)
    }
}