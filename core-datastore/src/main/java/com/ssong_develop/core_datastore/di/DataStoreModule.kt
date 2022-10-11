package com.ssong_develop.core_datastore.di

import com.ssong_develop.core_datastore.DataStoreRepository
import com.ssong_develop.core_datastore.DataStoreRepositoryImpl
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
    internal abstract fun bindDataStore(rickMortyDataStoreImpl: DataStoreRepositoryImpl): DataStoreRepository
}