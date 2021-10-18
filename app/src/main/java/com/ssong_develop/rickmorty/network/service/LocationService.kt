package com.ssong_develop.rickmorty.network.service

import com.ssong_develop.rickmorty.entities.Location
import com.ssong_develop.rickmorty.entities.base.Info
import com.ssong_develop.rickmorty.entities.base.Wrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationService {

    @GET("location")
    suspend fun fetchLocations(
        @Query("page") page: Int
    ): Wrapper<Info, Location>
}