package com.ssong_develop.rickmorty.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssong_develop.rickmorty.databinding.ItemCharacterBinding
import com.ssong_develop.rickmorty.network.client.Characters

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
            Glide.with(ivCharacterImage.context)
                .load(data.image)
                .into(ivCharacterImage)
            tvCharacterName.text = data.name
        }
    }
    override fun onClick(v: View?) {
        delegate.onItemClick(binding.ivCharacterImage,characters)
    }

    override fun onLongClick(v: View?): Boolean = false
}