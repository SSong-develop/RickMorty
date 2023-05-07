package com.ssong_develop.feature_search

import com.ssong_develop.core_model.RickMortyCharacter

interface SearchItemClickDelegate {
    fun onItemClick(
        characters: RickMortyCharacter
    )
}