package com.ssong_develop.core_data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ssong_develop.core_common.di.IoDispatcher
import com.ssong_develop.core_data.network.calladapter.common.NetworkResponseAdapterFactory
import com.ssong_develop.core_data.network.calladapter.flow.FlowCallAdapterFactory
import com.ssong_develop.core_data.network.client.CharacterClient
import com.ssong_develop.core_data.network.datasource.CharacterDataSource
import com.ssong_develop.core_data.network.service.CharacterService
import com.ssong_develop.core_data.repository.CharacterRepository
import com.ssong_develop.core_data.repository.NetworkResourceCharacterRepository
import com.ssong_develop.core_database.CharacterDao
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
    @ApiResponseFlowRetrofit
    @Provides
    @Singleton
    fun provideApiResponseFlowRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(json.asConverterFactory(contentType = "application/json".toMediaType()))
            .addCallAdapterFactory(FlowCallAdapterFactory.create())
            .client(provideOkHttpClient())
            .build()
    }

    @ExperimentalSerializationApi
    @NetworkResponseRetrofit
    @Provides
    @Singleton
    fun provideNetworkResponseRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(json.asConverterFactory(contentType = "application/json".toMediaType()))
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .client(provideOkHttpClient())
            .build()
    }

    @Provides
    @Singleton
    @ApiResponseFlowCharacterService
    fun provideApiResponseFlowCharacterService(
        @ApiResponseFlowRetrofit retrofit: Retrofit
    ): CharacterService =
        retrofit.create(CharacterService::class.java)

    @Provides
    @Singleton
    @NetworkResponseCharacterService
    fun provideNetworkResponseCharacterService(
        @NetworkResponseRetrofit retrofit: Retrofit
    ): CharacterService =
        retrofit.create(CharacterService::class.java)

    @Provides
    @Singleton
    fun provideCharacterClient(
        @ApiResponseFlowCharacterService service: CharacterService
    ) = CharacterClient(service)

    @Provides
    @Singleton
    fun provideCharacterDataSource(
        @NetworkResponseCharacterService service: CharacterService
    ) = CharacterDataSource(service)

    @Provides
    @Singleton
    fun provideCharacterRepository(
        client: CharacterClient,
        dao: CharacterDao,
        @ApiResponseFlowCharacterService characterService: CharacterService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ) =
        CharacterRepository(client, dao, characterService, ioDispatcher)

    @Provides
    @Singleton
    fun provideNetworkResourceCharacterRepository(
        dataSource: CharacterDataSource,
        @ApiResponseFlowCharacterService characterService: CharacterService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ) = NetworkResourceCharacterRepository(dataSource, characterService, ioDispatcher)
}