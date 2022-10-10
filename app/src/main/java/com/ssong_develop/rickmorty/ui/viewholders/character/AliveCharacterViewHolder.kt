package com.ssong_develop.rickmorty.ui.viewholders.character

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.core_model.Characters
import com.ssong_develop.rickmorty.databinding.ItemAliveCharacterBinding

class AliveCharacterViewHolder(
    private val binding: ItemAliveCharacterBinding,
    private val itemClickDelegate: ItemClickDelegate
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {
    private lateinit var character: Characters

    init {
        binding.root.setOnClickListener(this)
        binding.root.setOnLongClickListener(this)
    }

    fun bind(data: Characters) {
        character = data
        binding.apply {
            character = data
            executePendingBindings()
        }
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    override fun onLongClick(v: View?): Boolean {
        TODO("Not yet implemented")
    }
}