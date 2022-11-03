package com.ssong_develop.feature_character.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssong_develop.core_common.Resource
import com.ssong_develop.core_data.repository.CharacterRepository
import com.ssong_develop.core_model.Characters
import com.ssong_develop.core_model.Episode
import com.ssong_develop.feature_character.delegate.FavoriteCharacterDelegate
import com.ssong_develop.core_common.WhileViewSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: CharacterRepository,
    private val favoriteCharacterDelegate: FavoriteCharacterDelegate
) : ViewModel(),
    FavoriteCharacterDelegate by favoriteCharacterDelegate {

    private val characterState = savedStateHandle.getStateFlow<Characters?>("character",null)

    private val characterEpisodeState = characterState.filterNotNull().map { it.episode }

    val selectCharacterStateFlow: StateFlow<Characters?> =
        characterState
            .stateIn(
                scope = viewModelScope,
                started = Eagerly,
                initialValue = null
            )

    val characterEpisodesFlow: StateFlow<Resource<List<Episode>>> =
        characterEpisodeState
            .flatMapLatest { episode ->
                repository.getEpisodes(episode)
            }
            .stateIn(
                scope = viewModelScope,
                started = WhileViewSubscribed,
                initialValue = Resource.loading(emptyList())
            )

    val isFavoriteCharacterStateFlow: StateFlow<Boolean> = combine(
        selectCharacterStateFlow,
        favCharacterFlow
    ) { selectCharacter, favCharacter ->
        if (favCharacter.data == null) false
        else {
            when (favCharacter.status) {
                Resource.Status.SUCCESS -> {
                    selectCharacter?.id?.let {
                        it == (favCharacter.data?.id ?: false)
                    } ?: false
                }
                else -> false
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileViewSubscribed,
        initialValue = false
    )

    // TODO(change to reactivly)
    fun onClickFavorite() {
        if (isFavoriteCharacterStateFlow.value) {
            clearFavCharacterId()
        } else {
            selectCharacterStateFlow.value?.let { favCharacter ->
                putFavCharacterId(favCharacter.id)
            } ?: Log.d("ssong-develop", "문제 발생")
        }
    }
}