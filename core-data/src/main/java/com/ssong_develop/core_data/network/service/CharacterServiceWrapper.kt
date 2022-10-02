package com.ssong_develop.core_data.network.service

import com.ssong_develop.core_data.ApiResponse
import com.ssong_develop.core_data.network.calladapter.common.NetworkResponse
import com.ssong_develop.core_model.Characters
import com.ssong_develop.core_model.Episode
import com.ssong_develop.core_model.base.Info
import com.ssong_develop.core_model.base.Wrapper
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface CharacterServiceWrapper {

    /**
     * use ApiResponse for google guide api response wrapper class
     */
    @GET("character")
    fun fetchCharactersApiResponse(
        @Query("page") page: Int
    ): Flow<ApiResponse<Wrapper<Info, Characters>>>

    @GET
    suspend fun fetchEpisodesCharactersApiResponse(
        @Url url: String
    ): Flow<ApiResponse<Episode>>

    /**
     * use NetworkResponse response wrapper class
     */
    @GET("character/{id}")
    suspend fun getCharacterNetworkResponse(
        @Path("id") characterId: Int
    ): NetworkResponse<Characters>

    @GET
    suspend fun getEpisodesNetworkResponse(
        @Url url: String
    ): NetworkResponse<Episode>
}