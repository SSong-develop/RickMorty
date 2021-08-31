package com.ssong_develop.rickmorty.network.client

import com.ssong_develop.rickmorty.entities.Episode
import com.ssong_develop.rickmorty.entities.base.Info
import com.ssong_develop.rickmorty.entities.base.Wrapper
import com.ssong_develop.rickmorty.network.ApiResponse
import com.ssong_develop.rickmorty.network.service.EpisodeService
import com.ssong_develop.rickmorty.network.transform
import javax.inject.Inject

class EpisodeClient @Inject constructor(
    private val service: EpisodeService
) {
    fun fetchEpisode(
        page: Int,
        onResult: (response: ApiResponse<Wrapper<Info, Episode>>) -> Unit
    ) {
        service.fetchEpisodes(page).transform(onResult)
    }
}