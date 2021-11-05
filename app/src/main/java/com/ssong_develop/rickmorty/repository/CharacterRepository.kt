package com.ssong_develop.rickmorty.repository

import androidx.annotation.VisibleForTesting
import androidx.annotation.WorkerThread
import com.ssong_develop.rickmorty.di.IoDispatcher
import com.ssong_develop.rickmorty.entities.Characters
import com.ssong_develop.rickmorty.entities.Episode
import com.ssong_develop.rickmorty.network.ApiEmptyResponse
import com.ssong_develop.rickmorty.network.ApiErrorResponse
import com.ssong_develop.rickmorty.network.ApiSuccessResponse
import com.ssong_develop.rickmorty.network.client.CharacterClient
import com.ssong_develop.rickmorty.persistence.CharacterDao
import com.ssong_develop.rickmorty.utils.asFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val characterClient: CharacterClient,
    private val characterDao: CharacterDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : Repository {

    // FIXME : Test value for initialize instance
    @VisibleForTesting
    val testValue = 1

    @WorkerThread
    fun loadCharacters(
        page: Int,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String) -> Unit
    ): Flow<List<Characters>> = flow {
        val characters = characterDao.getCharacters(page)
        if (characters.isEmpty()) {
            val response = characterClient.fetchCharacters(page).results
            response.forEach { it.page = page }
            characterDao.insertCharacterList(response)
            emit(response)
        } else {
            emit(characters)
        }
    }.catch {
        onError("Api Response Error")
        emit(emptyList())
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)

    @WorkerThread
    fun loadEpisodes(
        episodeUrls: List<String>,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String) -> Unit
    ): Flow<List<Episode>> = flow {
        val response = mutableListOf<Episode>()
        episodeUrls.forEach {
            val data = characterClient.fetchEpisodesCharacters(it)
            response.add(data)
        }
        emit(response)
    }.catch {
        onError("Api Response Error")
        emit(mutableListOf())
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)
}