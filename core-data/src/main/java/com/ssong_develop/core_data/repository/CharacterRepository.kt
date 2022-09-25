package com.ssong_develop.core_data.repository

import androidx.annotation.WorkerThread
import com.ssong_develop.core_common.Resource
import com.ssong_develop.core_data.ApiResponse
import com.ssong_develop.core_data.ApiSuccessResponse
import com.ssong_develop.core_data.NetworkBoundResource
import com.ssong_develop.core_data.network.client.CharacterClient
import com.ssong_develop.core_data.network.pagingsource.CharacterPagingSource
import com.ssong_develop.core_database.CharacterDao
import com.ssong_develop.core_model.Characters
import com.ssong_develop.core_model.Episode
import com.ssong_develop.core_model.base.Info
import com.ssong_develop.core_model.base.Wrapper
import com.ssong_develop.rickmorty.di.IoDispatcher
import com.ssong_develop.rickmorty.repository.Repository
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
        onFetchFailed: (String) -> Unit
    ): Flow<Resource<List<Characters>>> {
        val networkBoundResource =
            object :
                NetworkBoundResource<List<Characters>, Wrapper<Info, Characters>>() {
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

    suspend fun getCharacter(
        characterId: Int
    ): Resource<Characters> = runCatching {
        characterClient.fetchCharacter(characterId)
    }.mapCatching {
        Resource.success(it)
    }.recoverCatching { throwable ->
        Resource.error("${throwable.message}", null)
    }.getOrElse { throwable -> Resource.error("${throwable.message}", null) }

    fun charactersPagingSource() = pagingSource
}