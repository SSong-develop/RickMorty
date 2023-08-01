package com.ssong_develop.feature_character.presentation.detail.viewholders

import com.ssong_develop.core_model.RickMortyCharacterEpisode

fun interface OnEpisodeClickListener {
    fun onClick(episode: RickMortyCharacterEpisode)
}