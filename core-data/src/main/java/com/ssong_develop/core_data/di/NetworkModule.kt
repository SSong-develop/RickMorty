package com.ssong_develop.core_data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ssong_develop.core_data.network.calladapter.common.NetworkResponseAdapterFactory
import com.ssong_develop.core_data.network.calladapter.flow.FlowCallAdapterFactory
import com.ssong_develop.core_data.network.service.CharacterServiceNoWrapper
import com.ssong_develop.core_data.network.service.CharacterServiceWrapper
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
    @ResponseNoWrapperRetrofit
    @Provides
    @Singleton
    fun provideApiResponseFlowRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(json.asConverterFactory(contentType = "application/json".toMediaType()))
            .client(provideOkHttpClient())
            .build()
    }

    @ExperimentalSerializationApi
    @ResponseWrapperRetrofit
    @Provides
    @Singleton
    fun provideNetworkResponseRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(json.asConverterFactory(contentType = "application/json".toMediaType()))
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addCallAdapterFactory(FlowCallAdapterFactory.create())
            .client(provideOkHttpClient())
            .build()
    }

    @Provides
    @Singleton
    fun provideResponseNoWrapperCharacterService(
        @ResponseNoWrapperRetrofit retrofit: Retrofit
    ): CharacterServiceNoWrapper =
        retrofit.create(CharacterServiceNoWrapper::class.java)

    @Provides
    @Singleton
    fun provideNetworkResponseCharacterService(
        @ResponseWrapperRetrofit retrofit: Retrofit
    ): CharacterServiceWrapper =
        retrofit.create(CharacterServiceWrapper::class.java)

}