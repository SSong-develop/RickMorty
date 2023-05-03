package com.ssong_develop.core_data.di

import com.ssong_develop.core_common.di.IoDispatcher
import com.ssong_develop.core_data.network.datasource.CharacterDataSourceNoWrapper
import com.ssong_develop.core_data.network.datasource.CharacterDataSourceWrapper
import com.ssong_develop.core_data.network.service.RickMortyCharacterService
import com.ssong_develop.core_data.network.service.SearchService
import com.ssong_develop.core_data.repository.CharacterRepository
import com.ssong_develop.core_data.repository.SearchRepository
import com.ssong_develop.core_database.dao.RickMortyCharacterDao
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
        characterDataSourceWrapper: CharacterDataSourceWrapper,
        characterDataSourceNoWrapper: CharacterDataSourceNoWrapper,
        dao: RickMortyCharacterDao,
        rickMortyCharacterService: RickMortyCharacterService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ) = CharacterRepository(
        characterDataSourceWrapper = characterDataSourceWrapper,
        characterDataSourceNoWrapper = characterDataSourceNoWrapper,
        rickMortyCharacterService = rickMortyCharacterService,
        ioDispatcher = ioDispatcher
    )

    @Provides
    @Singleton
    fun provideSearchRepository(
        searchService: SearchService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ) = SearchRepository(
        service = searchService,
        ioDispatcher = ioDispatcher
    )
}