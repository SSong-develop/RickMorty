package com.ssong_develop.rickmorty.network.client

import com.ssong_develop.rickmorty.entities.WrapperCharacter
import com.ssong_develop.rickmorty.network.ApiResponse
import com.ssong_develop.rickmorty.network.service.CharacterService
import com.ssong_develop.rickmorty.network.transform
import javax.inject.Inject

class CharacterClient @Inject constructor(
    private val service: CharacterService
) {
    fun fetchCharacters(
        onResult: (response: ApiResponse<WrapperCharacter>) -> Unit
    ) {
        service.fetchCharacters().transform(onResult)
    }
}