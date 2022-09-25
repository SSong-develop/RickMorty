package com.ssong_develop.core_data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ssong_develop.core_data.network.calladapter.FlowCallAdapterFactory
import com.ssong_develop.core_data.network.client.CharacterClient
import com.ssong_develop.core_data.network.pagingsource.CharacterPagingSource
import com.ssong_develop.core_data.network.service.CharacterService
import com.ssong_develop.core_data.repository.CharacterRepository
import com.ssong_develop.core_database.CharacterDao
import com.ssong_develop.rickmorty.di.IoDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val json by lazy {
        Json { coerceInputValues = true }
    }

    private val loggingInterceptor
        get() = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    private fun provideOkHttpClient() =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    @ExperimentalSerializationApi
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(json.asConverterFactory(contentType = "application/json".toMediaType()))
            .addCallAdapterFactory(FlowCallAdapterFactory.create())
            .client(provideOkHttpClient())
            .build()
    }

    @Provides
    @Singleton
    fun provideCharacterService(retrofit: Retrofit): CharacterService =
        retrofit.create(CharacterService::class.java)

    @Provides
    @Singleton
    fun provideCharacterClient(service: CharacterService) = CharacterClient(service)

    @Provides
    @Singleton
    fun provideCharacterRepository(
        client: CharacterClient,
        dao: CharacterDao,
        pageDataSource: CharacterPagingSource,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ) =
        CharacterRepository(client, dao, pageDataSource, ioDispatcher)
}