package com.ssong_develop.core_data.service

import com.ssong_develop.core_data.model.NetworkResult
import com.ssong_develop.core_data.model.NetworkRickMortyCharacter
import com.ssong_develop.core_data.model.NetworkRickMortyCharacterEpisode
import com.ssong_develop.core_data.model.NetworkRickMortyCharacterInfo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface CharacterService {
    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int
    ): NetworkResult<NetworkRickMortyCharacterInfo, NetworkRickMortyCharacter>

    @GET("character/{id}")
    suspend fun getCharacter(
        @Path("id") characterId: Int
    ): NetworkRickMortyCharacter

    @GET
    suspend fun getEpisodes(
        @Url url: String
    ): NetworkRickMortyCharacterEpisode
}