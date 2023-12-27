package com.ssong_develop.core_data.service

import com.ssong_develop.core_data.model.NetworkResult
import com.ssong_develop.core_data.model.NetworkRickMortyCharacter
import com.ssong_develop.core_data.model.NetworkRickMortyCharacterInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("character/")
    suspend fun searchCharacter(
        @Query("page") page: Int,
        @Query("name") name: String
    ): NetworkResult<NetworkRickMortyCharacterInfo, NetworkRickMortyCharacter>
}