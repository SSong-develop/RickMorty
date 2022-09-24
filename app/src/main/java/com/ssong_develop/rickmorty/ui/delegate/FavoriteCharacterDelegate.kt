package com.ssong_develop.rickmorty.ui.delegate

import com.ssong_develop.rickmorty.di.ApplicationScope
import com.ssong_develop.rickmorty.di.IoDispatcher
import com.ssong_develop.rickmorty.entities.Characters
import com.ssong_develop.rickmorty.persistence.datastore.RickMortyDataStore
import com.ssong_develop.rickmorty.repository.CharacterRepository
import com.ssong_develop.rickmorty.vo.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import javax.inject.Inject

interface FavoriteCharacterDelegate {
    val favCharacterFlow: Flow<Resource<Characters>>

    suspend fun clearFavCharacterId()

    suspend fun putFavCharacterId(id: Int)
}

class FavoriteCharacterDelegateImpl @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val rickMortyDataStore: RickMortyDataStore,
    @ApplicationScope private val applicationScope: CoroutineScope,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : FavoriteCharacterDelegate,
    RickMortyDataStore by rickMortyDataStore {

    companion object {
        private const val INVALID_CHARACTER_ID = -1
    }

    override val favCharacterFlow: Flow<Resource<Characters>> =
        favoriteCharacterIdFlow
            .filterNotNull()
            .filter { it != INVALID_CHARACTER_ID }
            .map { id -> characterRepository.getCharacter(id) }
            .flowOn(ioDispatcher)

    override suspend fun clearFavCharacterId() {
        rickMortyDataStore.clearFavoriteCharacterId()
    }

    override suspend fun putFavCharacterId(id: Int) {
        rickMortyDataStore.putFavoriteCharacterId(id)
    }
}
