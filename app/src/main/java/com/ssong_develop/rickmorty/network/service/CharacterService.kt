package com.ssong_develop.rickmorty.network.service

import com.ssong_develop.rickmorty.entities.Character
import com.ssong_develop.rickmorty.entities.WrapperCharacter
import com.ssong_develop.rickmorty.network.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterService {

    @GET("character")
    fun fetchCharacters() : Call<WrapperCharacter>

    @GET("character")
    fun fetchCharactersMore(
        @Query("page") page : Int
    ) : Call<WrapperCharacter>
}