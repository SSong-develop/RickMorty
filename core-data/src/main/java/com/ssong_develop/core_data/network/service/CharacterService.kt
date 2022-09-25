package com.ssong_develop.core_data.network.service

import com.ssong_develop.core_data.ApiResponse
import com.ssong_develop.core_model.Characters
import com.ssong_develop.core_model.Episode
import com.ssong_develop.core_model.base.Info
import com.ssong_develop.core_model.base.Wrapper
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface CharacterService {

    @GET("character")
    fun fetchCharacters(
        @Query("page") page: Int
    ): Flow<ApiResponse<Wrapper<Info, Characters>>>

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int
    ): Wrapper<Info, Characters>

    @GET("character/{id}")
    suspend fun getCharacter(
        @Path("id") characterId: Int
    ): Characters

    @GET
    suspend fun fetchEpisodesCharacters(
        @Url url: String
    ): Episode
}