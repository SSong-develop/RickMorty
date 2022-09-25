package com.ssong_develop.rickmorty.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssong_develop.rickmorty.di.IoDispatcher
import com.ssong_develop.rickmorty.entities.Characters
import com.ssong_develop.rickmorty.entities.Episode
import com.ssong_develop.rickmorty.repository.CharacterRepository
import com.ssong_develop.rickmorty.ui.delegate.FavoriteCharacterDelegate
import com.ssong_develop.rickmorty.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val repository: CharacterRepository,
    private val favoriteCharacterDelegate: FavoriteCharacterDelegate,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel(),
    FavoriteCharacterDelegate by favoriteCharacterDelegate {

    private val _selectCharacterSharedFlow: MutableSharedFlow<Characters> =
        MutableSharedFlow(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    val selectCharacterStateFlow: StateFlow<Characters?> =
        _selectCharacterSharedFlow
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = null
            )

    private val _characterEpisodeSharedFlow: MutableSharedFlow<List<String>> =
        MutableSharedFlow(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    val characterEpisodesFlow: StateFlow<List<Episode>> =
        _characterEpisodeSharedFlow.flatMapLatest { episode ->
            episode.run {
                repository.loadEpisodes(
                    this,
                    onStart = {},
                    onComplete = {},
                    onError = {}
                )
            }
        }.stateIn(
            scope = viewModelScope,
            started = Eagerly,
            initialValue = emptyList()
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
        started = Eagerly,
        initialValue = false
    )

    fun postCharacter(character: Characters) {
        _characterEpisodeSharedFlow.tryEmit(character.episode)
        _selectCharacterSharedFlow.tryEmit(character)
    }

    // TODO(change to reactivly)
    fun onClickFavorite() {
        viewModelScope.launch {
            if (isFavoriteCharacterStateFlow.value) {
                clearFavCharacterId()
            } else {
                selectCharacterStateFlow.value?.let { favCharacter ->
                    putFavCharacterId(favCharacter.id)
                } ?: Log.d("ssong-develop", "문제 발생")
            }
        }
    }
}