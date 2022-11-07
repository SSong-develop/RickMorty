package com.ssong_develop.core_data.repository

import com.ssong_develop.core_data.network.service.CharacterSearchService
import com.ssong_develop.rickmorty.repository.Repository
import javax.inject.Inject

class SearchCharacterRepository @Inject constructor(
    private val service: CharacterSearchService
) : Repository {

}