package com.ssong_develop.rickmorty.repository

import androidx.lifecycle.MutableLiveData
import com.ssong_develop.rickmorty.AppExecutors
import com.ssong_develop.rickmorty.entities.Characters
import com.ssong_develop.rickmorty.network.ApiResponse
import com.ssong_develop.rickmorty.network.client.CharacterClient
import com.ssong_develop.rickmorty.network.message
import com.ssong_develop.rickmorty.persistence.CharacterDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val characterClient: CharacterClient,
    private val characterDao: CharacterDao,
    private val appExecutors: AppExecutors
) : Repository {

    fun loadCharacters(page: Int, error: (String) -> Unit): MutableStateFlow<List<Characters>> {
        var characters = emptyList<Characters>()
        appExecutors.diskIO().execute {
            characters = characterDao.getCharacters()
        }
        val stateFlow = MutableStateFlow<List<Characters>>(characters)
        characterClient.fetchCharacters(page) { response ->
            when (response) {
                is ApiResponse.Success -> {
                    response.data?.let { data ->
                        characters = data.results
                        appExecutors.mainThread().execute {
                            stateFlow.value = data.results
                        }
                        appExecutors.diskIO().execute {
                            characterDao.insertCharacterList(characters)
                        }
                    }
                }
                is ApiResponse.Failure.Error -> error(response.message())
                is ApiResponse.Failure.Exception -> error(response.message())
            }
        }
        stateFlow.value = characters
        return stateFlow
    }
}