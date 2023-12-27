package com.ssong_develop.feature_character.presentation.character.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.ssong_develop.feature_character.R
import com.ssong_develop.feature_character.databinding.ItemCharacterBinding
import com.ssong_develop.feature_character.model.RickMortyCharacterTransitionAnimModel
import com.ssong_develop.feature_character.model.RickMortyCharacterUiModel
import com.ssong_develop.feature_character.presentation.character.viewholders.CharacterViewHolder

private val characterDiffItemCallback =
    object : DiffUtil.ItemCallback<RickMortyCharacterUiModel>() {
        override fun areItemsTheSame(
            oldItem: RickMortyCharacterUiModel,
            newItem: RickMortyCharacterUiModel
        ): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: RickMortyCharacterUiModel,
            newItem: RickMortyCharacterUiModel
        ): Boolean =
            oldItem == newItem
    }

internal class CharacterPagingAdapter(
    private val onCharacterClick: (model: RickMortyCharacterTransitionAnimModel) -> Unit
) : PagingDataAdapter<RickMortyCharacterUiModel, CharacterViewHolder>(
    characterDiffItemCallback
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemCharacterBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_character, parent, false)
        return CharacterViewHolder(binding, onCharacterClick)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}
