package com.ssong_develop.core_data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ssong_develop.core_data.datasource.CharacterDataSource
import com.ssong_develop.core_data.datasource.CharacterPagingSource
import com.ssong_develop.core_data.model.NetworkRickMortyCharacter
import com.ssong_develop.core_data.model.asModel
import com.ssong_develop.core_data.remotemediator.CharacterRemoteMediator
import com.ssong_develop.core_data.service.CharacterService
import com.ssong_develop.core_database.database.RickMortyCharacterDatabase
import com.ssong_develop.core_database.model.LocalEntityRickMortyCharacter
import com.ssong_develop.core_model.RickMortyCharacter
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class CharacterRepository @Inject constructor(
    private val database: RickMortyCharacterDatabase,
    private val characterDataSource: CharacterDataSource,
    private val characterService: CharacterService
) {
    fun getCharacterStream(): Flow<PagingData<RickMortyCharacter>> =
        Pager(
            config = PagingConfig(
                pageSize = CHARACTER_PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                CharacterPagingSource(characterService)
            }
        ).flow

    fun databaseCharacterStream(): Flow<PagingData<LocalEntityRickMortyCharacter>> =
        Pager(
            config = PagingConfig(
                pageSize = CHARACTER_PAGE_SIZE,
                enablePlaceholders = true
            ),
            remoteMediator = CharacterRemoteMediator(
                database,
                characterService
            )
        ) {
            database.characterDao().getCharacterPagingSource()
        }.flow

    suspend fun getEpisodes(urls: List<String>) =
        characterDataSource.getCharacterEpisode(urls)

    companion object {
        private const val CHARACTER_PAGE_SIZE = 10
    }
}