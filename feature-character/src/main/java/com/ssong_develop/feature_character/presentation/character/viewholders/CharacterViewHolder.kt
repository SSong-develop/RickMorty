package com.ssong_develop.feature_character.presentation.character.viewholders

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ssong_develop.core_common.extension.asBitmap
import com.ssong_develop.feature_character.databinding.ItemCharacterBinding
import com.ssong_develop.feature_character.model.RickMortyCharacterTransitionAnimModel
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
            Glide.with(root.context)
                .load(rickMortyCharacter.image)
                .listener(
                    object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>,
                            isFirstResource: Boolean
                        ): Boolean = false

                        override fun onResourceReady(
                            resource: Drawable,
                            model: Any,
                            target: Target<Drawable>?,
                            dataSource: DataSource,
                            isFirstResource: Boolean
                        ): Boolean {
                            val palette = Palette.from(resource.asBitmap()).generate()
                            val dominantColor = palette.getDominantColor(Color.BLACK)
                            rickMortyCharacter = rickMortyCharacter.copy(dominantColor = dominantColor)
                            cardView.setCardBackgroundColor(dominantColor)
                            return false
                        }
                    }
                ).into(ivCharacterImage)
        }.also { binding -> binding.executePendingBindings() }
    }

    override fun onClick(view: View) {
        onClickCharacterListener.onClick(
            RickMortyCharacterTransitionAnimModel(
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