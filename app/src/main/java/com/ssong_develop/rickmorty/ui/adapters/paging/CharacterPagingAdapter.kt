package com.ssong_develop.rickmorty.ui.adapters.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.ssong_develop.core_model.Characters
import com.ssong_develop.core_model.Characters.Companion.ALIVE_CHARACTER
import com.ssong_develop.core_model.Characters.Companion.DEAD_CHARACTER
import com.ssong_develop.core_model.Characters.Companion.EXCEPTIONAL_CHARACTER
import com.ssong_develop.core_model.Characters.Companion.UNKNOWN_CHARACTER
import com.ssong_develop.rickmorty.R
import com.ssong_develop.rickmorty.databinding.ItemCharacterBinding
import com.ssong_develop.rickmorty.ui.viewholders.character.CharacterListViewHolder
import com.ssong_develop.rickmorty.utils.Status

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
        return when (viewType) {
            DEAD_CHARACTER -> {
                val binding: ItemCharacterBinding =
                    DataBindingUtil.inflate(layoutInflater, R.layout.item_character, parent, false)
                return CharacterListViewHolder(binding, delegate)
            }
            ALIVE_CHARACTER -> {
                val binding: ItemCharacterBinding =
                    DataBindingUtil.inflate(layoutInflater, R.layout.item_character, parent, false)
                return CharacterListViewHolder(binding, delegate)
            }
            UNKNOWN_CHARACTER -> {
                val binding: ItemCharacterBinding =
                    DataBindingUtil.inflate(layoutInflater, R.layout.item_character, parent, false)
                return CharacterListViewHolder(binding, delegate)
            }
            else -> {
                throw IllegalArgumentException("No Type")
            }
        }
    }

    override fun onBindViewHolder(holder: CharacterListViewHolder, position: Int) {
        when (holder) {

        }
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemViewType(position: Int): Int = when(getItem(position)?.type) {
        Status.DEAD.status -> DEAD_CHARACTER
        Status.ALIVE.status -> ALIVE_CHARACTER
        Status.UNKNOWN.status -> UNKNOWN_CHARACTER
        else -> EXCEPTIONAL_CHARACTER
    }
}
