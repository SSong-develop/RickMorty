package com.ssong_develop.feature_character.delegate

import com.ssong_develop.core_common.Resource
import com.ssong_develop.core_common.WhileViewSubscribed
import com.ssong_develop.core_common.di.ApplicationScope
import com.ssong_develop.core_common.di.IoDispatcher
import com.ssong_develop.core_data.repository.CharacterRepository
import com.ssong_develop.core_datastore.DataStoreRepository
import com.ssong_develop.core_model.Characters
import com.ssong_develop.core_model.Episode
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

interface FavoriteCharacterDelegate {
    val favoriteCharacterState: StateFlow<Characters?>

    val favCharacterEpisodeFlow: StateFlow<Resource<List<Episode>>>

    fun clearFavCharacter()

    fun putFavCharacter(character: Characters)
}

@ExperimentalCoroutinesApi
internal class FavoriteCharacterDelegateImpl @Inject constructor(
    private val characterRepository: CharacterRepository,
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

    override val favCharacterEpisodeFlow: StateFlow<Resource<List<Episode>>> =
        favoriteCharacterState
            .flatMapLatest { character ->
                characterRepository.getEpisodesNetworkResponse(
                    character?.episode ?: emptyList()
                )
            }
            .stateIn(
                scope = applicationScope,
                started = SharingStarted.Eagerly,
                initialValue = Resource.loading(emptyList())
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
