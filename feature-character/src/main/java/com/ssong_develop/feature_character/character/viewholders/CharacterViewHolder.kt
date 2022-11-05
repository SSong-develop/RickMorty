package com.ssong_develop.feature_character.character.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.core_model.Characters
import com.ssong_develop.feature_character.databinding.ItemCharacterBinding

class CharacterViewHolder(
    private val binding: ItemCharacterBinding,
    private val itemClickDelegate: ItemClickDelegate
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

    private lateinit var characters: Characters

    init {
        binding.root.setOnClickListener(this)
        binding.root.setOnLongClickListener(this)
    }

    fun bind(data: Characters) {
        characters = data
        binding.apply {
            character = data
            executePendingBindings()
        }
    }

    override fun onClick(view: View) {
        itemClickDelegate.onItemClick(
            characterImageView = binding.ivCharacterImage,
            characterNameView = binding.tvCharacterName,
            characterImageTransitionName = binding.ivCharacterImage.transitionName,
            characterNameTransitionName = binding.tvCharacterName.transitionName,
            characters = characters
        )
    }

    override fun onLongClick(v: View?): Boolean = false
}