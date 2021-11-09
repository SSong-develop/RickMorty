package com.ssong_develop.rickmorty.network.client

import com.ssong_develop.rickmorty.network.service.CharacterService
import javax.inject.Inject

class CharacterClient @Inject constructor(
    private val service: CharacterService
) {

    fun fetchCharacters(page: Int) = service.fetchCharacters(page)

    suspend fun fetchEpisodesCharacters(episodeUrl: String) =
        service.fetchEpisodesCharacters(episodeUrl)
}