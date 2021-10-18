package com.ssong_develop.rickmorty.network.client

import com.ssong_develop.rickmorty.network.service.CharacterService
import javax.inject.Inject

class CharacterClient @Inject constructor(
    private val service: CharacterService
) {
    suspend fun suspendFetchCharacters(page: Int) = service.fetchCharacters(page)
}