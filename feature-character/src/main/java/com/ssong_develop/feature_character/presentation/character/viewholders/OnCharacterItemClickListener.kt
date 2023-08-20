package com.ssong_develop.feature_character.presentation.character.viewholders

import com.ssong_develop.feature_character.model.RickMortyCharacterTransitionModel

internal fun interface OnCharacterItemClickListener {
    fun onClick(model: RickMortyCharacterTransitionModel)
}