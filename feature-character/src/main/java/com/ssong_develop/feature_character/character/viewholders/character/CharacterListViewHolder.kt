package com.ssong_develop.feature_character.character.viewholders.character

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.core_model.Characters
import com.ssong_develop.feature_character.databinding.ItemCharacterBinding

class CharacterListViewHolder(
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
        itemClickDelegate.onItemClick(binding.ivCharacterImage, characters)
    }

    override fun onLongClick(v: View?): Boolean = false
}