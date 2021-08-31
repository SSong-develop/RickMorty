package com.ssong_develop.rickmorty.network.client

import com.ssong_develop.rickmorty.entities.Character
import com.ssong_develop.rickmorty.entities.base.Info
import com.ssong_develop.rickmorty.entities.base.Wrapper
import com.ssong_develop.rickmorty.network.ApiResponse
import com.ssong_develop.rickmorty.network.service.CharacterService
import com.ssong_develop.rickmorty.network.transform
import javax.inject.Inject

class CharacterClient @Inject constructor(
    private val service: CharacterService
) {
    fun fetchCharacters(
        page: Int,
        onResult: (response: ApiResponse<Wrapper<Info, Character>>) -> Unit
    ) {
        service.fetchCharacters(page).transform(onResult)
    }
}