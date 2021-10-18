package com.ssong_develop.rickmorty.network.client

import com.ssong_develop.rickmorty.entities.Characters
import com.ssong_develop.rickmorty.entities.base.Info
import com.ssong_develop.rickmorty.entities.base.Wrapper
import com.ssong_develop.rickmorty.network.ApiResponse
import com.ssong_develop.rickmorty.network.service.CharacterService
import com.ssong_develop.rickmorty.network.transform
import javax.inject.Inject

class CharacterClient @Inject constructor(
    private val service: CharacterService
) {
    suspend fun suspendFetchCharacters(page: Int) = service.fetchCharacters(page)
}