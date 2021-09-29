package com.ssong_develop.rickmorty.repository

import androidx.lifecycle.MutableLiveData
import com.ssong_develop.rickmorty.AppExecutors
import com.ssong_develop.rickmorty.entities.Characters
import com.ssong_develop.rickmorty.entities.Episode
import com.ssong_develop.rickmorty.network.ApiResponse
import com.ssong_develop.rickmorty.network.client.EpisodeClient
import com.ssong_develop.rickmorty.network.message
import com.ssong_develop.rickmorty.persistence.EpisodeDao
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class EpisodeRepository @Inject constructor(
    private val client: EpisodeClient,
    private val episodeDao : EpisodeDao,
    private val appExecutors : AppExecutors
) : Repository {

    fun loadEpisodes(page: Int, error: (String) -> Unit): MutableStateFlow<List<Episode>> {
        var episodes = emptyList<Episode>()
        appExecutors.diskIO().execute {
            episodes = episodeDao.getEpisodes()
        }
        val stateFlow = MutableStateFlow<List<Episode>>(episodes)
        client.fetchEpisode(page) { response ->
            when (response) {
                is ApiResponse.Success -> {
                    response.data?.let { data ->
                        episodes = data.results
                        appExecutors.mainThread().execute {
                            stateFlow.value = data.results
                        }
                        appExecutors.diskIO().execute {
                            episodeDao.insertEpisodeList(episodes)
                        }
                    }
                }
                is ApiResponse.Failure.Error -> error(response.message())
                is ApiResponse.Failure.Exception -> error(response.message())
            }
        }
        stateFlow.value = episodes
        return stateFlow
    }
}