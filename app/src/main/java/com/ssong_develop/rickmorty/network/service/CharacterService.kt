package com.ssong_develop.rickmorty.network.service

import com.ssong_develop.rickmorty.entities.WrapperCharacter
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterService {

    @GET("character")
    fun fetchCharacters(
        @Query("page") page: Int
    ): Call<WrapperCharacter>
}