package com.ssong_develop.core_datastore.di

import com.ssong_develop.core_datastore.RickMortyDataStore
import com.ssong_develop.core_datastore.RickMortyDataStoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreModule {

    @Binds
    @Singleton
    internal abstract fun bindDataStore(rickMortyDataStoreImpl: RickMortyDataStoreImpl): RickMortyDataStore
}