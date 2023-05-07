package com.ssong_develop.core_data.di

import com.ssong_develop.core_common.di.IoDispatcher
import com.ssong_develop.core_data.datasource.CharacterDataSource
import com.ssong_develop.core_data.service.CharacterService
import com.ssong_develop.core_data.service.SearchService
import com.ssong_develop.core_data.repository.CharacterRepository
import com.ssong_develop.core_data.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideCharacterRepository(
        characterDataSource: CharacterDataSource,
        characterService: CharacterService
    ) = CharacterRepository(
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