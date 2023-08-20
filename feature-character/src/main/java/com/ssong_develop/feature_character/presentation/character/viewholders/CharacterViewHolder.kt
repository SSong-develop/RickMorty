package com.ssong_develop.feature_character.presentation.character.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.feature_character.databinding.ItemCharacterBinding
import com.ssong_develop.feature_character.model.RickMortyCharacterTransitionModel
import com.ssong_develop.feature_character.model.RickMortyCharacterUiModel

internal class CharacterViewHolder(
    private val binding: ItemCharacterBinding,
    private val onClickCharacterListener: OnCharacterItemClickListener
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

    private lateinit var rickMortyCharacter: RickMortyCharacterUiModel

    init {
        binding.root.setOnClickListener(this)
        binding.root.setOnLongClickListener(this)
    }

    fun bind(data: RickMortyCharacterUiModel) {
        rickMortyCharacter = data
        binding.apply {
            character = data
            executePendingBindings()
        }
    }

    override fun onClick(view: View) {
        onClickCharacterListener.onClick(
            RickMortyCharacterTransitionModel(
                character = rickMortyCharacter,
                viewAndTransitionNameList = arrayOf(
                    binding.ivCharacterImage to binding.ivCharacterImage.transitionName,
                    binding.tvCharacterName to binding.tvCharacterName.transitionName
                )
            )
        )
    }

    override fun onLongClick(v: View?): Boolean = false
}