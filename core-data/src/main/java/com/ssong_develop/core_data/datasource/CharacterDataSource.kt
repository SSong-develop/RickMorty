package com.ssong_develop.core_data.datasource

import com.ssong_develop.core_common.RateLimiter
import com.ssong_develop.core_data.service.CharacterService
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CharacterDataSource @Inject constructor(
    private val service: CharacterService
) {
    private val dataSourceRateLimiter =
        RateLimiter<String>(timeout = 1, timeUnit = TimeUnit.SECONDS)

    suspend fun getCharacterEpisode(urls: List<String>) =
        urls.takeIf { dataSourceRateLimiter.shouldFetch(urls.first()) }
            ?.map { url -> service.getEpisodes(url) }
}