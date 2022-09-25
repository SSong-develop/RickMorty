package com.ssong_develop.rickmorty.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.ssong_develop.core_model.Characters
import com.ssong_develop.rickmorty.R
import com.ssong_develop.rickmorty.databinding.ItemCharacterBinding
import com.ssong_develop.rickmorty.ui.viewholders.CharacterListViewHolder

private val characterDiffItemCallback = object : DiffUtil.ItemCallback<Characters>() {
    override fun areItemsTheSame(oldItem: Characters, newItem: Characters): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Characters, newItem: Characters): Boolean =
        oldItem.id == newItem.id
}

class CharacterPagingAdapter(
    private val delegate: CharacterListViewHolder.Delegate
) : PagingDataAdapter<Characters, CharacterListViewHolder>(
    characterDiffItemCallback
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemCharacterBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_character, parent, false)
        return CharacterListViewHolder(binding, delegate)
    }

    override fun onBindViewHolder(holder: CharacterListViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}