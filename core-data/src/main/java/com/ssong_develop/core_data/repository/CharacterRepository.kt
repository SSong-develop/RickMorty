package com.ssong_develop.core_data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ssong_develop.core_data.datasource.CharacterDataSource
import com.ssong_develop.core_data.datasource.CharacterPagingSource
import com.ssong_develop.core_data.model.NetworkRickMortyCharacter
import com.ssong_develop.core_data.service.CharacterService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val characterDataSource: CharacterDataSource,
    private val characterService: CharacterService
) {
    fun getCharacterStream(): Flow<PagingData<NetworkRickMortyCharacter>> =
        Pager(
            config = PagingConfig(
                pageSize = CHARACTER_PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                CharacterPagingSource(characterService)
            }
        ).flow

    suspend fun getEpisodes(urls: List<String>) =
        characterDataSource.getCharacterEpisode(urls)

    companion object {
        private const val CHARACTER_PAGE_SIZE = 10
    }
}