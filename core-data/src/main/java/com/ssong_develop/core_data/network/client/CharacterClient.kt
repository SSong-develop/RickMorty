package com.ssong_develop.core_data.network.client

import com.ssong_develop.core_data.network.service.CharacterService
import javax.inject.Inject

class CharacterClient @Inject constructor(
    private val service: CharacterService
) {

    fun fetchCharacters(page: Int) = service.fetchCharacters(page)

    suspend fun fetchEpisodesCharacters(episodeUrl: String) =
        service.fetchEpisodesCharacters(episodeUrl)

    suspend fun fetchCharacter(id: Int) = service.getCharacter(id)
}