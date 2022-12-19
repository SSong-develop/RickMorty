package com.ssong_develop.feature_character.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssong_develop.core_common.WhileViewSubscribed
import com.ssong_develop.core_data.repository.CharacterRepository
import com.ssong_develop.core_model.Characters
import com.ssong_develop.core_model.Episode
import com.ssong_develop.feature_character.delegate.FavoriteCharacterDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

data class CharacterDetailUiState(
    val character: Characters? = null,
    val characterEpisode: List<Episode> = emptyList(),
    val isEpisodeLoading: Boolean = false,
)

sealed class CharacterDetailEvent {
    object Back : CharacterDetailEvent()
    data class ShowToast(val message: String) : CharacterDetailEvent()
}

@ExperimentalCoroutinesApi
@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: CharacterRepository,
    private val favoriteCharacterDelegate: FavoriteCharacterDelegate
) : ViewModel(), FavoriteCharacterDelegate by favoriteCharacterDelegate {

    companion object {
        private const val CHARACTER_KEY = "character"
        private const val SECOND = 1_000L
        private const val DEFAULT_TIMEOUT_SECOND = 20 * SECOND
        private const val TIME_OUT_ERROR_MESSAGE = "시간초과 됐습니다."
    }

    private val _characterDetailEventBus = MutableSharedFlow<CharacterDetailEvent>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val characterDetailUiEventBus = _characterDetailEventBus.asSharedFlow()

    private val _uiState = MutableStateFlow<CharacterDetailUiState>(CharacterDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        savedStateHandle.get<Characters>(CHARACTER_KEY)?.run {
            updateCharacter(this)
            getCharacterEpisode(episode)
        }
    }

    val isFavoriteCharacterState: StateFlow<Boolean> = combine(
        uiState,
        favoriteCharacterState
    ) { uiState, favCharacter ->
        if (favCharacter == null || uiState.character == null) {
            false
        } else {
            favCharacter.id == uiState.character.id
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileViewSubscribed,
        initialValue = false
    )

    private fun getCharacterEpisode(episodeUrls: List<String>) {
        viewModelScope.launch {
            runCatching {
                updateEpisodeLoading(true)
                withTimeout(DEFAULT_TIMEOUT_SECOND) {
                    repository.getEpisodes(episodeUrls)
                }
            }.onSuccess { episodes ->
                updateEpisodeLoading(false)
                updateCharacterEpisode(episodes)
            }.onFailure { throwable ->
                updateEpisodeLoading(false)
                when (throwable) {
                    is TimeoutCancellationException -> {
                        postShowToastEvent(TIME_OUT_ERROR_MESSAGE)
                        updateCharacterEpisode(emptyList())
                    }
                    else -> {
                        updateCharacterEpisode(emptyList())
                    }
                }
            }
        }
    }

    private fun updateCharacter(character: Characters) {
        _uiState.value = _uiState.value.copy(
            character = character
        )
    }

    private fun updateCharacterEpisode(resource: List<Episode>) {
        _uiState.value = _uiState.value.copy(
            characterEpisode = resource
        )
    }

    private fun updateEpisodeLoading(loadingValue: Boolean) {
        _uiState.value = _uiState.value.copy(
            isEpisodeLoading = loadingValue
        )
    }

    fun postBackEvent() {
        _characterDetailEventBus.tryEmit(CharacterDetailEvent.Back)
    }

    fun postShowToastEvent(message: String) {
        _characterDetailEventBus.tryEmit(CharacterDetailEvent.ShowToast(message))
    }

    fun onClickFavorite() {
        if (isFavoriteCharacterState.value) {
            clearFavCharacter()
        } else {
            _uiState.value.character?.let { favCharacter ->
                putFavCharacter(favCharacter)
            }
        }
    }
}


