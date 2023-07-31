package com.ssong_develop.core_datastore.di

import com.ssong_develop.core_datastore.PreferenceStorage
import com.ssong_develop.core_datastore.DataStorePreferenceStorage
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
    internal abstract fun bindDataStore(dataStorePreferenceStorage: DataStorePreferenceStorage): PreferenceStorage
}