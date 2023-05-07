package com.ssong_develop.core_data.di

import com.ssong_develop.core_data.datasource.CharacterDataSource
import com.ssong_develop.core_data.service.CharacterService
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
    fun provideCharacterDataSource(
        service: CharacterService
    ) = CharacterDataSource(service)
}