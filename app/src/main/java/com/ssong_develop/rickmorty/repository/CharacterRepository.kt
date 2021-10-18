package com.ssong_develop.rickmorty.repository

import com.ssong_develop.rickmorty.di.IoDispatcher
import com.ssong_develop.rickmorty.entities.Characters
import com.ssong_develop.rickmorty.network.client.CharacterClient
import com.ssong_develop.rickmorty.persistence.CharacterDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val characterClient: CharacterClient,
    private val characterDao: CharacterDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : Repository {

    fun loadCharacters(
        page: Int,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String) -> Unit
    ): Flow<List<Characters>> = flow {
        val response = characterClient.suspendFetchCharacters(page).results
/*            characterDao.insertCharacterList(response)
            val characters = characterDao.getCharacters()*/
        emit(response)
    }.catch {
        onError("Api Response Error")
        emit(emptyList())
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)
}