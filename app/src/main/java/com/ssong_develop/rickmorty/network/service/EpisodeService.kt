package com.ssong_develop.rickmorty.network.service

import com.ssong_develop.rickmorty.entities.Episode
import com.ssong_develop.rickmorty.entities.base.Info
import com.ssong_develop.rickmorty.entities.base.Wrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EpisodeService {

    @GET("episode")
    fun fetchEpisodes(
        @Query("page") page: Int
    ): Call<Wrapper<Info, Episode>>
}