package com.ssong_develop.rickmorty.repository

import com.ssong_develop.rickmorty.di.IoDispatcher
import com.ssong_develop.rickmorty.entities.Episode
import com.ssong_develop.rickmorty.network.client.EpisodeClient
import com.ssong_develop.rickmorty.persistence.EpisodeDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class EpisodeRepository @Inject constructor(
    private val client: EpisodeClient,
    private val episodeDao: EpisodeDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : Repository {

    fun loadEpisodes(
        page: Int,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String) -> Unit
    ): Flow<List<Episode>> = flow {
        val episodes = episodeDao.getEpisodes(page)
        if(episodes.isEmpty()){
            val response = client.fetchEpisode(page).results
            response.forEach { it.page = page }
            episodeDao.insertEpisodeList(response)
            emit(response)
        }else {
            emit(episodes)
        }
    }.catch {
        onError("Api Response Error")
        emit(emptyList())
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)
}