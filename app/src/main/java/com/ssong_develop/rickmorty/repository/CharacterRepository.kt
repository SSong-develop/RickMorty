package com.ssong_develop.rickmorty.repository

import com.ssong_develop.rickmorty.di.IoDispatcher
import com.ssong_develop.rickmorty.network.client.CharacterClient
import com.ssong_develop.rickmorty.entities.Characters
import com.ssong_develop.rickmorty.persistence.CharacterDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val characterClient: CharacterClient,
    private val characterDao: CharacterDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : Repository {

    suspend fun loadCharacters(
        page : Int,
        onStart: () -> Unit,
        onComplete: () -> Unit
    ) : List<Characters> = withContext(ioDispatcher) {
        onStart()
        val characters = characterDao.getCharacters(page)
        if(characters.isEmpty()){
            val response = characterClient.fetchCharacter(page).results
            response.forEach { it.page = page }
            characterDao.insertCharacterList(response)
            onComplete()
            response
        }else {
            onComplete()
            characters
        }
    }
}