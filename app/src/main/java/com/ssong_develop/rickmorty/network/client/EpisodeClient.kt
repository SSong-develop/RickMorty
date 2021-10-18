package com.ssong_develop.rickmorty.network.client

import com.ssong_develop.rickmorty.network.service.EpisodeService
import javax.inject.Inject

class EpisodeClient @Inject constructor(
    private val service: EpisodeService
) {
    suspend fun fetchEpisode(page: Int) = service.fetchEpisodes(page)
}