package com.ssong_develop.core_data.di

import com.ssong_develop.core_data.network.pagingsource.CharacterPagingSource
import com.ssong_develop.core_data.network.service.CharacterService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideCharacterPagingSource(
        characterService: CharacterService
    ) = CharacterPagingSource(characterService)
}