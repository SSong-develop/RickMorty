package com.ssong_develop.rickmorty.ui.delegate

import com.ssong_develop.core_common.Resource
import com.ssong_develop.core_data.repository.CharacterRepository
import com.ssong_develop.core_datastore.RickMortyDataStore
import com.ssong_develop.core_model.Characters
import com.ssong_develop.core_common.di.ApplicationScope
import com.ssong_develop.core_common.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

interface FavoriteCharacterDelegate {
    val favCharacterFlow: Flow<Resource<Characters>>

    val favCharacterSheetVisibilityFlow: StateFlow<Boolean>

    fun clearFavCharacterId()

    fun putFavCharacterId(id: Int)
}

internal class FavoriteCharacterDelegateImpl @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val rickMortyDataStore: RickMortyDataStore,
    @ApplicationScope private val applicationScope: CoroutineScope,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : FavoriteCharacterDelegate,
    RickMortyDataStore by rickMortyDataStore {

    override val favCharacterFlow: Flow<Resource<Characters>> =
        favoriteCharacterIdFlow
            .map { id ->
                id?.let { characterRepository.getCharacter(id) } ?: Resource.error(
                    "Id is Invalid",
                    null
                )
            }
            .flowOn(ioDispatcher)
            .stateIn(
                scope = applicationScope,
                started = SharingStarted.Eagerly,
                initialValue = Resource.loading(null)
            )

    override val favCharacterSheetVisibilityFlow: StateFlow<Boolean> =
        favCharacterFlow.map { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> true
                else -> false
            }
        }.stateIn(
            scope = applicationScope,
            started = SharingStarted.Eagerly,
            initialValue = false
        )

    override fun clearFavCharacterId() {
        applicationScope.launch {
            rickMortyDataStore.clearFavoriteCharacterId()
        }
    }

    override fun putFavCharacterId(id: Int) {
        applicationScope.launch {
            rickMortyDataStore.putFavoriteCharacterId(id)
        }
    }
}
