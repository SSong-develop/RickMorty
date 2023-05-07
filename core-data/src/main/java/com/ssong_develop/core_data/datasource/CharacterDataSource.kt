package com.ssong_develop.core_data.datasource

import com.ssong_develop.core_data.service.CharacterService
import javax.inject.Inject

class CharacterDataSource @Inject constructor(
    private val service: CharacterService
) {
    suspend fun getCharacterEpisode(urls: List<String>) =
        urls.map { url -> service.getEpisodes(url) }
}