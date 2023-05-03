package com.ssong_develop.core_data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ssong_develop.core_common.Resource
import com.ssong_develop.core_common.di.IoDispatcher
import com.ssong_develop.core_data.calladapter.networkresponse.NetworkResponse
import com.ssong_develop.core_data.network.datasource.CharacterDataSourceNoWrapper
import com.ssong_develop.core_data.network.datasource.CharacterDataSourceWrapper
import com.ssong_develop.core_data.network.pagingsource.CharacterPagingSource
import com.ssong_develop.core_data.network.service.RickMortyCharacterService
import com.ssong_develop.core_model.Characters
import com.ssong_develop.core_model.Episode
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val characterDataSourceWrapper: CharacterDataSourceWrapper,
    private val characterDataSourceNoWrapper: CharacterDataSourceNoWrapper,
    private val rickMortyCharacterService: RickMortyCharacterService,
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
        pagingSourceFactory = { CharacterPagingSource(rickMortyCharacterService) }
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
}