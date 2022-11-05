package com.ssong_develop.core_data.network.datasource.client

import com.ssong_develop.core_data.network.service.CharacterServiceWrapper
import javax.inject.Inject

class CharacterDataSourceWrapper @Inject constructor(
    private val service: CharacterServiceWrapper
) {
    /**
     * ApiResponse
     */
    suspend fun getCharacterNetworkResponse(id: Int) = service.getCharacterNetworkResponse(id)

    suspend fun getCharacterEpisodeNetworkResponse(urls: List<String>) =
        urls.map { url ->
            service.getEpisodesNetworkResponse(url)
        }
}