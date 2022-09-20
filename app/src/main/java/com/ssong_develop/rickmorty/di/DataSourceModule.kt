package com.ssong_develop.rickmorty.di

import com.ssong_develop.rickmorty.network.pagingsource.CharacterPagingSource
import com.ssong_develop.rickmorty.network.service.CharacterService
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