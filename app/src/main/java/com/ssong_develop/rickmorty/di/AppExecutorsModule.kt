package com.ssong_develop.rickmorty.di

import com.ssong_develop.rickmorty.AppExecutors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppExecutorsModule {

    @Provides
    @Singleton
    fun provideAppExecutors() = AppExecutors()

}