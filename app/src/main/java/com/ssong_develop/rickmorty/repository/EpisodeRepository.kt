package com.ssong_develop.rickmorty.repository

import androidx.lifecycle.MutableLiveData
import com.ssong_develop.rickmorty.AppExecutors
import com.ssong_develop.rickmorty.entities.Episode
import com.ssong_develop.rickmorty.network.ApiResponse
import com.ssong_develop.rickmorty.network.client.EpisodeClient
import com.ssong_develop.rickmorty.network.message
import com.ssong_develop.rickmorty.persistence.EpisodeDao
import javax.inject.Inject

class EpisodeRepository @Inject constructor(
    private val client: EpisodeClient,
    private val episodeDao : EpisodeDao,
    private val appExecutors : AppExecutors
) : Repository {

    fun loadEpisodes(page: Int, error: (String) -> Unit): MutableLiveData<List<Episode>> {
        val liveData = MutableLiveData<List<Episode>>()
        var episodes = episodeDao.getEpisodes()
        client.fetchEpisode(page) { response ->
            when (response) {
                is ApiResponse.Success -> {
                    response.data?.let { data ->
                        episodes = data.results
                        liveData.postValue(data.results)
                        appExecutors.diskIO().execute {
                            episodeDao.insertEpisodeList(episodes)
                        }
                    }
                }
                is ApiResponse.Failure.Error -> error(response.message())
                is ApiResponse.Failure.Exception -> error(response.message())
            }
        }
        liveData.postValue(episodes)
        return liveData
    }
}