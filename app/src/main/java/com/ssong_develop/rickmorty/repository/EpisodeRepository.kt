package com.ssong_develop.rickmorty.repository

import androidx.lifecycle.MutableLiveData
import com.ssong_develop.rickmorty.entities.Episode
import com.ssong_develop.rickmorty.network.ApiResponse
import com.ssong_develop.rickmorty.network.client.EpisodeClient
import com.ssong_develop.rickmorty.network.message
import com.ssong_develop.rickmorty.persistence.EpisodeDao
import javax.inject.Inject

class EpisodeRepository @Inject constructor(
    private val client: EpisodeClient,
    private val episodeDao : EpisodeDao
) : Repository {
    override var isLoading = false

    fun loadEpisodes(page: Int, error: (String) -> Unit): MutableLiveData<List<Episode>> {
        val liveData = MutableLiveData<List<Episode>>()
        isLoading = true
        client.fetchEpisode(page) { response ->
            isLoading = false
            when (response) {
                is ApiResponse.Success -> {
                    response.data?.let { data ->
                        liveData.postValue(data.results)
                    }
                }
                is ApiResponse.Failure.Error -> error(response.message())
                is ApiResponse.Failure.Exception -> error(response.message())
            }
        }
        return liveData
    }
}