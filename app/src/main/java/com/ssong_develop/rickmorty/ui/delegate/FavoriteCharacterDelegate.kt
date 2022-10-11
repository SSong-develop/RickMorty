package com.ssong_develop.rickmorty.ui.delegate

import com.ssong_develop.core_common.Resource
import com.ssong_develop.core_common.di.ApplicationScope
import com.ssong_develop.core_common.di.IoDispatcher
import com.ssong_develop.core_data.repository.CharacterRepository
import com.ssong_develop.core_datastore.DataStoreRepository
import com.ssong_develop.core_model.Characters
import com.ssong_develop.core_model.Episode
import com.ssong_develop.rickmorty.utils.WhileViewSubscribed
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

interface FavoriteCharacterDelegate {
    val favCharacterFlow: Flow<Resource<Characters>>

    val favCharacterSheetVisibilityFlow: StateFlow<Boolean>

    val favCharacterEpisodeFlow: Flow<List<Resource<Episode>>>

    fun clearFavCharacterId()

    fun putFavCharacterId(id: Int)
}

@ExperimentalCoroutinesApi
internal class FavoriteCharacterDelegateImpl @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val dataStoreRepository: DataStoreRepository,
    @ApplicationScope private val applicationScope: CoroutineScope,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : FavoriteCharacterDelegate,
    DataStoreRepository by dataStoreRepository {

    override val favCharacterFlow: SharedFlow<Resource<Characters>> =
        favoriteCharacterIdFlow
            .flatMapLatest { id ->
                id?.let {
                    characterRepository.getCharacterNetworkResponse(id)
                } ?: flowOf(
                    Resource.error(
                        "Id is Invalid",
                        null
                    )
                )
            }
            .flowOn(ioDispatcher)
            .shareIn(
                scope = applicationScope,
                started = WhileViewSubscribed,
                replay = 1
            )

    override val favCharacterSheetVisibilityFlow: StateFlow<Boolean> =
        favCharacterFlow
            .map { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> true
                    else -> false
                }
            }
            .stateIn(
                scope = applicationScope,
                started = WhileViewSubscribed,
                initialValue = false
            )

    override val favCharacterEpisodeFlow: Flow<List<Resource<Episode>>> =
        favCharacterFlow
            .flatMapLatest { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        characterRepository.getEpisodesNetworkResponse(
                            resource.data?.episode ?: emptyList()
                        )
                    }
                    else -> {
                        flow { emit(listOf(Resource.loading(null))) }
                    }
                }
            }
            .flowOn(ioDispatcher)
            .shareIn(
                scope = applicationScope,
                started = WhileViewSubscribed,
                replay = 1
            )

    override fun clearFavCharacterId() {
        applicationScope.launch {
            dataStoreRepository.clearFavoriteCharacterId()
        }
    }

    override fun putFavCharacterId(id: Int) {
        applicationScope.launch {
            dataStoreRepository.putFavoriteCharacterId(id)
        }
    }
}
