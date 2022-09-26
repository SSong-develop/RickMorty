package com.ssong_develop.core_data.network.datasource

import com.ssong_develop.core_data.di.NetworkResponseCharacterService
import com.ssong_develop.core_data.network.service.CharacterService
import javax.inject.Inject

class CharacterDataSource @Inject constructor(
    @NetworkResponseCharacterService private val service: CharacterService
) {
    suspend fun getCharacter(page: Int) =
        service.getCharacterNetworkResponse(page)
}