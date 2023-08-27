package com.ssong_develop.core_data.di

import androidx.paging.ExperimentalPagingApi
import com.ssong_develop.core_data.datasource.CharacterDataSource
import com.ssong_develop.core_data.repository.CharacterRepository
import com.ssong_develop.core_data.repository.SearchRepository
import com.ssong_develop.core_data.service.CharacterService
import com.ssong_develop.core_data.service.SearchService
import com.ssong_develop.core_database.database.RickMortyCharacterDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@ExperimentalPagingApi
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideCharacterRepository(
        rickMortyCharacterDatabase: RickMortyCharacterDatabase,
        characterDataSource: CharacterDataSource,
        characterService: CharacterService
    ) = CharacterRepository(
        database = rickMortyCharacterDatabase,
        characterDataSource = characterDataSource,
        characterService = characterService
    )

    @Provides
    @Singleton
    fun provideSearchRepository(
        searchService: SearchService
    ) = SearchRepository(
        service = searchService
    )
}