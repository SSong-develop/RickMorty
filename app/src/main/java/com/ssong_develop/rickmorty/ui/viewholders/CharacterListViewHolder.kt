package com.ssong_develop.rickmorty.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.core_model.Characters
import com.ssong_develop.rickmorty.databinding.ItemCharacterBinding

class CharacterListViewHolder(
    private val binding: ItemCharacterBinding,
    private val delegate: Delegate
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

    private lateinit var characters: Characters

    init {
        binding.root.setOnClickListener(this)
        binding.root.setOnLongClickListener(this)
    }

    interface Delegate {
        fun onItemClick(view: View, characters: Characters)
    }

    fun bind(data: Characters) {
        characters = data
        binding.apply {
            character = data
            executePendingBindings()
        }
    }

    override fun onClick(view: View) {
        delegate.onItemClick(binding.ivCharacterImage, characters)
    }

    override fun onLongClick(v: View?): Boolean = false
}