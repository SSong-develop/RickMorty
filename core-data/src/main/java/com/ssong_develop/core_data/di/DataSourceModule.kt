package com.ssong_develop.core_data.di

import com.ssong_develop.core_data.network.datasource.CharacterDataSourceNoWrapper
import com.ssong_develop.core_data.network.datasource.CharacterDataSourceWrapper
import com.ssong_develop.core_data.network.service.CharacterServiceNoWrapper
import com.ssong_develop.core_data.network.service.CharacterServiceWrapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    /**
     * Separate DataSource for api networking with use flow or just common data type with wrapping response class
     */
    @Provides
    @Singleton
    fun provideCharacterDataSourceWrapper(
        service: CharacterServiceWrapper
    ) = CharacterDataSourceWrapper(service)

    @Provides
    @Singleton
    fun provideCharacterDataSourceNoWrapper(
        service: CharacterServiceNoWrapper
    ) = CharacterDataSourceNoWrapper(service)
}