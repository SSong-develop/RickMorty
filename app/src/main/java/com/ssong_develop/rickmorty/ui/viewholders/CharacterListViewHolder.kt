package com.ssong_develop.rickmorty.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
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
            /** Glide Image Load */
            Glide.with(ivCharacterImage.context)
                .load(data.image)
                .into(ivCharacterImage)
            /** Glide palette background Color */
            Glide.with(ivCharacterImage.context)
                .load(data.image)
                .listener(
                    GlidePalette.with(data.image)
                        .use(BitmapPalette.Profile.MUTED_LIGHT)
                        .intoCallBack { palette ->
                            val rgb = palette?.dominantSwatch?.rgb
                            if (rgb != null) {
                                cardView.setBackgroundColor(rgb)
                            }
                        }.crossfade(true)
                ).into(ivCharacterImage)
            /** Character Name text */
            tvCharacterName.text = data.name
        }
    }

    override fun onClick(v: View?) {
        delegate.onItemClick(binding.ivCharacterImage, characters)
    }

    override fun onLongClick(v: View?): Boolean = false
}