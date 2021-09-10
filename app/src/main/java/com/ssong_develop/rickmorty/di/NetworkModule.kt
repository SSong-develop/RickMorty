package com.ssong_develop.rickmorty.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ssong_develop.rickmorty.AppExecutors
import com.ssong_develop.rickmorty.network.client.CharacterClient
import com.ssong_develop.rickmorty.network.client.EpisodeClient
import com.ssong_develop.rickmorty.network.client.LocationClient
import com.ssong_develop.rickmorty.network.service.CharacterService
import com.ssong_develop.rickmorty.network.service.EpisodeService
import com.ssong_develop.rickmorty.network.service.LocationService
import com.ssong_develop.rickmorty.persistence.CharacterDao
import com.ssong_develop.rickmorty.persistence.EpisodeDao
import com.ssong_develop.rickmorty.persistence.LocationDao
import com.ssong_develop.rickmorty.repository.CharacterRepository
import com.ssong_develop.rickmorty.repository.EpisodeRepository
import com.ssong_develop.rickmorty.repository.LocationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val json by lazy {
        Json { coerceInputValues = true }
    }

    @ExperimentalSerializationApi
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(json.asConverterFactory(contentType = "application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideCharacterService(retrofit: Retrofit): CharacterService =
        retrofit.create(CharacterService::class.java)

    @Provides
    @Singleton
    fun provideLocationService(retrofit: Retrofit): LocationService =
        retrofit.create(LocationService::class.java)

    @Provides
    @Singleton
    fun provideEpisodeService(retrofit: Retrofit): EpisodeService =
        retrofit.create(EpisodeService::class.java)

    @Provides
    @Singleton
    fun provideCharacterClient(service: CharacterService) = CharacterClient(service)

    @Provides
    @Singleton
    fun provideLocationClient(service: LocationService) = LocationClient(service)

    @Provides
    @Singleton
    fun provideEpisodeClient(service: EpisodeService) = EpisodeClient(service)

    @Provides
    @Singleton
    fun provideCharacterRepository(
        client: CharacterClient,
        dao: CharacterDao,
        executors: AppExecutors
    ) =
        CharacterRepository(client, dao, executors)

    @Provides
    @Singleton
    fun provideLocationRepository(
        client: LocationClient,
        dao: LocationDao,
        executors: AppExecutors
    ) =
        LocationRepository(client, dao, executors)

    @Provides
    @Singleton
    fun provideEpisodeRepository(client: EpisodeClient, dao: EpisodeDao, executors: AppExecutors) =
        EpisodeRepository(client, dao, executors)
}