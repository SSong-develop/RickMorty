package com.ssong_develop.rickmorty.network.service

import com.ssong_develop.rickmorty.entities.Characters
import com.ssong_develop.rickmorty.entities.Episode
import com.ssong_develop.rickmorty.entities.base.Info
import com.ssong_develop.rickmorty.entities.base.Wrapper
import com.ssong_develop.rickmorty.network.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface CharacterService {

    @GET("character")
    suspend fun fetchCharacters(
        @Query("page") page: Int
    ): Wrapper<Info, Characters>

    @GET
    suspend fun fetchEpisodesCharacters(
        @Url url : String
    ): Episode
}