package com.ssong_develop.core_data.repository

import androidx.paging.*
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
import kotlinx.coroutines.flow.map
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
    )
        .flow
        .flowOn(ioDispatcher)

    /**
     * NoWrapper Function Scope
     */
    fun getCharacter(id: Int) = flow {
        emit(
            runCatching {
                characterDataSourceNoWrapper.getCharacter(id)
            }.mapCatching { response ->
                Resource.success(response)
            }.getOrElse { throwable ->
                Resource.error(msg = throwable.message.toString(), data = null)
            }
        )
    }.flowOn(ioDispatcher)

    fun getEpisodes(urls: List<String>) = flow {
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

    /**
     * Wrapper Function Scope
     */
    fun getCharacterNetworkResponse(id: Int) = flow {
        emit(
            runCatching {
                characterDataSourceWrapper.getCharacterNetworkResponse(id)
            }.mapCatching { wrapperResponse ->
                when (wrapperResponse) {
                    is NetworkResponse.ApiEmptyResponse -> {
                        Resource.success(null)
                    }
                    is NetworkResponse.ApiFailureResponse -> {
                        Resource.error("UnKnown Error", null)
                    }
                    is NetworkResponse.ApiSuccessResponse -> {
                        Resource.success(wrapperResponse.body)
                    }
                }
            }.getOrElse { throwable ->
                Resource.error(throwable.message.toString(), null)
            }
        )
    }.flowOn(ioDispatcher)

    fun getEpisodesNetworkResponse(urls: List<String>) = flow {
        val apiSuccessEpisodes = mutableListOf<Resource<Episode>>()
        val apiErrorEpisodes = mutableListOf<Resource<Episode>>()
        runCatching {
            characterDataSourceWrapper.getCharacterEpisodeNetworkResponse(urls)
        }.mapCatching { wrapperResponse ->
            wrapperResponse.map {
                when (it) {
                    is NetworkResponse.ApiEmptyResponse -> {
                        apiSuccessEpisodes.add(Resource.success(it.body))
                    }
                    is NetworkResponse.ApiFailureResponse -> {
                        apiErrorEpisodes.add(Resource.error("UnKnown Error",null))
                    }
                    is NetworkResponse.ApiSuccessResponse -> {
                        apiSuccessEpisodes.add(Resource.success(it.body))
                    }
                }
            }
        }
        if (apiSuccessEpisodes.isEmpty()) {
            if (apiErrorEpisodes.isEmpty()) {
                emit(listOf<Resource<Episode>>(element = Resource.success(null)))
            } else {
                emit(apiErrorEpisodes)
            }
        } else {
            emit(apiSuccessEpisodes)
        }
    }.flowOn(ioDispatcher)
}