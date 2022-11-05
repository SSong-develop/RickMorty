package com.ssong_develop.feature_character.character.adapters

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
import com.ssong_develop.feature_character.R
import com.ssong_develop.feature_character.Status
import com.ssong_develop.feature_character.character.viewholders.CharacterViewHolder
import com.ssong_develop.feature_character.character.viewholders.ItemClickDelegate
import com.ssong_develop.feature_character.databinding.ItemCharacterBinding

private val characterDiffItemCallback = object : DiffUtil.ItemCallback<Characters>() {
    override fun areItemsTheSame(oldItem: Characters, newItem: Characters): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Characters, newItem: Characters): Boolean =
        oldItem.id == newItem.id
}

class CharacterPagingAdapter(
    private val delegate: ItemClickDelegate
) : PagingDataAdapter<Characters, CharacterViewHolder>(
    characterDiffItemCallback
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemCharacterBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_character, parent, false)
        return CharacterViewHolder(binding, delegate)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)?.type) {
        Status.DEAD.status -> DEAD_CHARACTER
        Status.ALIVE.status -> ALIVE_CHARACTER
        Status.UNKNOWN.status -> UNKNOWN_CHARACTER
        else -> EXCEPTIONAL_CHARACTER
    }
}
