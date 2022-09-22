package com.ssong_develop.rickmorty.di

import com.ssong_develop.rickmorty.persistence.datastore.RickMortyDataStore
import com.ssong_develop.rickmorty.persistence.datastore.RickMortyDataStoreImpl
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
    abstract fun bindDataStore(rickMortyDataStoreImpl: RickMortyDataStoreImpl): RickMortyDataStore
}