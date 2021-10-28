package com.ssong_develop.rickmorty.network.service

import com.ssong_develop.rickmorty.entities.base.Info
import com.ssong_develop.rickmorty.entities.base.Wrapper
import com.ssong_develop.rickmorty.network.client.Characters
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterService {

    @GET("character")
    suspend fun fetchCharacter(
        @Query("page") page : Int
    ) : Wrapper<Info, Characters>
}