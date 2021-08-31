package com.ssong_develop.rickmorty.repository

import androidx.lifecycle.MutableLiveData
import com.ssong_develop.rickmorty.entities.Character
import com.ssong_develop.rickmorty.entities.WrapperCharacter
import com.ssong_develop.rickmorty.network.ApiResponse
import com.ssong_develop.rickmorty.network.client.CharacterClient
import com.ssong_develop.rickmorty.network.message
import timber.log.Timber
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val characterClient: CharacterClient
) : Repository {
    override var isLoading: Boolean = false

    init {
        Timber.d("Injection CharacterRepository")
    }

    fun loadCharacters(page: Int, error: (String) -> Unit): MutableLiveData<List<Character>> {
        val liveData = MutableLiveData<List<Character>>()
        isLoading = true
        characterClient.fetchCharacters(page) { response ->
            isLoading = false
            when (response) {
                is ApiResponse.Success -> {
                    response.data?.let { data ->
                        liveData.postValue(data.character)
                    }
                }
                is ApiResponse.Failure.Error -> error(response.message())
                is ApiResponse.Failure.Exception -> error(response.message())
            }
        }

        return liveData
    }
}