package com.ssong_develop.core_data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ssong_develop.core_data.service.CharacterService
import com.ssong_develop.core_data.service.SearchService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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

    @Provides
    @Singleton
    fun provideJson(): Json =
        Json {
            coerceInputValues = true
            prettyPrint = true
        }

    @Provides
    @Singleton
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    @ExperimentalSerializationApi
    @Provides
    @Singleton
    fun provideRickMortyRetrofit(
        json: Json,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(json.asConverterFactory(contentType = "application/json".toMediaType()))
            .client(provideOkHttpClient(httpLoggingInterceptor))
            .build()

    @Provides
    @Singleton
    fun provideRickMortyCharacterService(retrofit: Retrofit): CharacterService =
        retrofit.create(CharacterService::class.java)

    @Provides
    @Singleton
    fun provideRickMortyCharacterSearchService(retrofit: Retrofit): SearchService =
        retrofit.create(SearchService::class.java)
}