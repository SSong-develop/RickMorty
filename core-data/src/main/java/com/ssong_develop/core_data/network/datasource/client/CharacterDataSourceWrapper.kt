package com.ssong_develop.core_data.network.datasource.client

import com.ssong_develop.core_data.ApiResponse
import com.ssong_develop.core_data.network.service.CharacterServiceWrapper
import com.ssong_develop.core_model.Characters
import com.ssong_develop.core_model.Episode
import com.ssong_develop.core_model.base.Info
import com.ssong_develop.core_model.base.Wrapper
import kotlinx.coroutines.flow.Flow
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