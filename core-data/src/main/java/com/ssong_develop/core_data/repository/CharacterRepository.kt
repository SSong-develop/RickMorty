package com.ssong_develop.core_data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ssong_develop.core_common.Resource
import com.ssong_develop.core_common.di.IoDispatcher
import com.ssong_develop.core_data.network.calladapter.common.NetworkResponse
import com.ssong_develop.core_data.network.datasource.CharacterDataSourceNoWrapper
import com.ssong_develop.core_data.network.datasource.client.CharacterDataSourceWrapper
import com.ssong_develop.core_data.network.pagingsource.CharacterPagingSource
import com.ssong_develop.core_data.network.service.CharacterServiceNoWrapper
import com.ssong_develop.core_database.CharacterDao
import com.ssong_develop.core_model.Characters
import com.ssong_develop.core_model.Episode
import com.ssong_develop.rickmorty.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val characterDataSourceWrapper: CharacterDataSourceWrapper,
    private val characterDataSourceNoWrapper: CharacterDataSourceNoWrapper,
    private val characterDao: CharacterDao,
    private val characterServiceNoWrapper: CharacterServiceNoWrapper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : Repository {

    companion object {
        private const val CHARACTER_PAGE_SIZE = 10
    }

    /**
     * PagingSource do not create by hilt, because we use refresh, then need to create new PagingSource
     */
    fun getCharacterStream(): Flow<PagingData<Characters>> = Pager(
        config = PagingConfig(pageSize = CHARACTER_PAGE_SIZE, enablePlaceholders = true),
        pagingSourceFactory = { CharacterPagingSource(characterServiceNoWrapper) }
    ).flow.flowOn(ioDispatcher)

    /**
     * NoWrapper Function Scope
     */
    fun getEpisodesStream(urls: List<String>) = flow {
        emit(
            runCatching {
                characterDataSourceNoWrapper.getCharacterEpisode(urls)
            }.mapCatching { responses ->
                Resource.success(responses)
            }.getOrElse { throwable ->
                Resource.error(throwable.message.toString(), emptyList())
            }
        )
    }.flowOn(ioDispatcher)

    suspend fun getEpisodes(urls: List<String>) =
        characterDataSourceNoWrapper.getCharacterEpisode(urls)

    /**
     * Wrapper Function Scope
     */
    fun getEpisodesNetworkResponse(urls: List<String>) = flow {
        val apiSuccessEpisodes = mutableListOf<Episode>()
        runCatching {
            characterDataSourceWrapper.getCharacterEpisodeNetworkResponse(urls)
        }.onSuccess { wrapperResponse ->
            wrapperResponse.map {
                when (it) {
                    is NetworkResponse.ApiSuccessResponse -> {
                        it.body.let { episode ->
                            apiSuccessEpisodes.add(episode)
                        }
                    }
                    is NetworkResponse.ApiEmptyResponse -> {}
                    is NetworkResponse.ApiFailureResponse -> {}

                }
            }
        }
        if (apiSuccessEpisodes.isEmpty()) {
            emit(Resource.error("api Error", emptyList<Episode>()))
        } else {
            emit(Resource.success(apiSuccessEpisodes))
        }
    }.flowOn(ioDispatcher)
}