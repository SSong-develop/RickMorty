package com.ssong_develop.core_data.network.service

import com.ssong_develop.core_data.ApiResponse
import com.ssong_develop.core_model.Characters
import com.ssong_develop.core_model.base.Info
import com.ssong_develop.core_model.base.Wrapper
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterSearchService {
    @GET("character/")
    fun searchCharacter(
        @Query("name") name: String
    ): Flow<Wrapper<Info, Characters>>
}