package com.ssong_develop.feature_character.di

import com.ssong_develop.core_data.repository.CharacterRepository
import com.ssong_develop.feature_character.detail.EpisodeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    internal fun provideEpisodeUseCase(repository: CharacterRepository) = EpisodeUseCase(repository)
}