package com.ssong_develop.core_data.repository

import com.ssong_develop.core_common.Resource
import com.ssong_develop.core_common.di.IoDispatcher
import com.ssong_develop.core_data.di.ApiResponseFlowCharacterService
import com.ssong_develop.core_data.network.calladapter.common.NetworkResponse
import com.ssong_develop.core_data.network.datasource.CharacterDataSource
import com.ssong_develop.core_data.network.pagingsource.CharacterPagingSource
import com.ssong_develop.core_data.network.service.CharacterService
import com.ssong_develop.core_model.Episode
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class NetworkResourceCharacterRepository @Inject constructor(
    private val dataSource: CharacterDataSource,
    @ApiResponseFlowCharacterService private val characterService: CharacterService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    // Paging
    fun getCharacters() = CharacterPagingSource(characterService = characterService)

    suspend fun getCharacter(id: Int) = runCatching {
        dataSource.getCharacter(id)
    }.mapCatching { networkResponse ->
        // In this scope, convert domain Entity or want to wrapping data
        when (networkResponse) {
            is NetworkResponse.ApiEmptyResponse -> Resource.success(null)
            is NetworkResponse.ApiFailureResponse -> Resource.error(networkResponse.errorMessage,null)
            is NetworkResponse.ApiSuccessResponse -> Resource.success(networkResponse.body)
        }
    }.getOrElse { throwable ->
        Resource.error(throwable.message ?: "UnKnown Error",null)
    }

    suspend fun getEpisodes(urls: List<String>) = runCatching {
        dataSource.getCharacterEpisode(urls)
    }.mapCatching { networkResponses ->
        val success = mutableListOf<Resource<Episode>>()
        val failed = mutableListOf<Resource<Nothing>>()
        networkResponses.forEach { networkResponse ->
            when (networkResponse) {
                is NetworkResponse.ApiEmptyResponse -> {
                    val resource = Resource.success(null)
                    success.add(resource)
                }
                is NetworkResponse.ApiFailureResponse -> {
                    val resource = Resource.error(networkResponse.errorMessage,null)
                    failed.add(resource)
                }
                is NetworkResponse.ApiSuccessResponse -> {
                    val resource = Resource.success(networkResponse.body)
                    success.add(resource)
                }
            }
        }
        success to failed
    }.getOrElse { throwable ->
        emptyList<Resource<Episode>>() to emptyList<Nothing>()
    }
}