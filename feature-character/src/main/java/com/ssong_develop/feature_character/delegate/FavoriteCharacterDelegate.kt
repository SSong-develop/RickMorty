package com.ssong_develop.feature_character.delegate

import com.ssong_develop.core_common.di.ApplicationScope
import com.ssong_develop.core_datastore.DataStoreRepository
import com.ssong_develop.core_model.Characters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

interface FavoriteCharacterDelegate {
    val favoriteCharacterState: StateFlow<Characters?>

    fun clearFavCharacter()

    fun putFavCharacter(character: Characters)
}

@ExperimentalCoroutinesApi
internal class FavoriteCharacterDelegateImpl @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    @ApplicationScope private val applicationScope: CoroutineScope
) : FavoriteCharacterDelegate {
    override val favoriteCharacterState: StateFlow<Characters?> =
        dataStoreRepository.favoriteCharacterFlow
            .stateIn(
                scope = applicationScope,
                started = SharingStarted.Eagerly,
                initialValue = null
            )

    override fun clearFavCharacter() {
        applicationScope.launch {
            dataStoreRepository.clearFavoriteCharacter()
        }
    }

    override fun putFavCharacter(character: Characters) {
        applicationScope.launch {
            dataStoreRepository.putFavoriteCharacter(character)
        }
    }
}
