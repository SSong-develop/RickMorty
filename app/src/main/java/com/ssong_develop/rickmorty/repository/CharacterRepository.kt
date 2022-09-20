package com.ssong_develop.rickmorty.repository

import androidx.annotation.VisibleForTesting
import androidx.annotation.WorkerThread
import com.ssong_develop.rickmorty.di.IoDispatcher
import com.ssong_develop.rickmorty.entities.Characters
import com.ssong_develop.rickmorty.entities.Episode
import com.ssong_develop.rickmorty.entities.base.Info
import com.ssong_develop.rickmorty.entities.base.Wrapper
import com.ssong_develop.rickmorty.network.ApiResponse
import com.ssong_develop.rickmorty.network.ApiSuccessResponse
import com.ssong_develop.rickmorty.network.NetworkBoundResource
import com.ssong_develop.rickmorty.network.NetworkResource
import com.ssong_develop.rickmorty.vo.Resource
import com.ssong_develop.rickmorty.network.client.CharacterClient
import com.ssong_develop.rickmorty.network.pagingsource.CharacterPagingSource
import com.ssong_develop.rickmorty.persistence.CharacterDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val characterClient: CharacterClient,
    private val characterDao: CharacterDao,
    private val pagingSource: CharacterPagingSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : Repository {

    @WorkerThread
    fun loadCharacters(
        page: Int,
        onFetchFailed : (String) -> Unit
    ): Flow<Resource<List<Characters>>> {
        val networkBoundResource =
            object : NetworkBoundResource<List<Characters>, Wrapper<Info, Characters>>() {
                override suspend fun saveRemoteData(item: Wrapper<Info, Characters>) {
                    item.results.forEach { it.page = page }
                    characterDao.insertCharacterList(item.results)
                }

                override suspend fun shouldFetchFromNetwork(data: List<Characters>?): Boolean {
                    return data == null || data.isEmpty()
                }

                override suspend fun fetchFromLocal(): Flow<List<Characters>> =
                    characterDao.getCharacters(page)

                override suspend fun fetchFromNetwork(): Flow<ApiResponse<Wrapper<Info, Characters>>> =
                    characterClient.fetchCharacters(page)

                override suspend fun onFetchFailed(errorBody: String?, statusCode: Int) {
                    errorBody?.let { onFetchFailed(it) }
                }

                override suspend fun processResponse(response: ApiSuccessResponse<Wrapper<Info, Characters>>) {
                    super.processResponse(response)
                }
            }.asFlow()
                .map {
                    when (it.status) {
                        Resource.Status.LOADING -> {
                            Resource.loading(null)
                        }
                        Resource.Status.SUCCESS -> {
                            val characters = it.data
                            Resource.success(characters)
                        }
                        Resource.Status.ERROR -> {
                            Resource.error(it.message!!, null)
                        }
                    }
                }
        return networkBoundResource.flowOn(ioDispatcher)
    }

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

    fun charactersPagingSource() = pagingSource
}