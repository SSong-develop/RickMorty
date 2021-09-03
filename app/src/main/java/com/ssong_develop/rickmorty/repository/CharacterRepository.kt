package com.ssong_develop.rickmorty.repository

import androidx.lifecycle.MutableLiveData
import com.ssong_develop.rickmorty.entities.Characters
import com.ssong_develop.rickmorty.network.ApiResponse
import com.ssong_develop.rickmorty.network.client.CharacterClient
import com.ssong_develop.rickmorty.network.message
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val characterClient: CharacterClient
) : Repository {
    override var isLoading: Boolean = false

    fun loadCharacters(page: Int, error: (String) -> Unit): MutableLiveData<List<Characters>> {
        val liveData = MutableLiveData<List<Characters>>()
        isLoading = true
        characterClient.fetchCharacters(page) { response ->
            isLoading = false
            when (response) {
                is ApiResponse.Success -> {
                    response.data?.let { data ->
                        liveData.postValue(data.results)
                    }
                }
                is ApiResponse.Failure.Error -> error(response.message())
                is ApiResponse.Failure.Exception -> error(response.message())
            }
        }

        return liveData
    }
}