package com.ssong_develop.core_data.network.service

import com.ssong_develop.core_model.Characters
import com.ssong_develop.core_model.base.Info
import com.ssong_develop.core_model.base.Wrapper
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("character/")
    suspend fun searchCharacter(
        @Query("page") page: Int,
        @Query("name") name: String
    ): Wrapper<Info, Characters>
}