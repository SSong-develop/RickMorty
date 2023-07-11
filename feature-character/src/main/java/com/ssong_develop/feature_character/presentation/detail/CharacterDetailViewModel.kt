package com.ssong_develop.feature_character.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.ssong_develop.core_common.WhileViewSubscribed
import com.ssong_develop.core_data.model.asModel
import com.ssong_develop.core_data.repository.CharacterRepository
import com.ssong_develop.core_model.RickMortyCharacterEpisode
import com.ssong_develop.feature_character.delegate.FavoriteCharacterDelegate
import com.ssong_develop.feature_character.model.RickMortyCharacterUiModel
import com.ssong_develop.feature_character.model.asModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CharacterDetailUiState(
    val character: RickMortyCharacterUiModel? = null,
    val characterEpisode: List<RickMortyCharacterEpisode> = emptyList(),
    val isLoading: Boolean = false,
)

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: CharacterRepository,
    private val favoriteCharacterDelegate: FavoriteCharacterDelegate
) : ViewModel(), FavoriteCharacterDelegate by favoriteCharacterDelegate {

    private val _uiState = MutableStateFlow<CharacterDetailUiState>(CharacterDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        savedStateHandle.get<RickMortyCharacterUiModel>(CHARACTER_KEY)?.let {
            updateCharacter(it)
            getCharacterEpisode(it.episode)
        } ?: run {
            // can't Update Characters. then we need to show the dummy character or some else

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
            updateEpisodeLoading(true)
            runCatching {
                repository.getEpisodes(episodeUrls)
            }.onSuccess { episodes ->
                updateEpisodeLoading(false)
                updateCharacterEpisode(episodes.map { it.asModel() })
            }.onFailure { throwable ->
                updateEpisodeLoading(false)
                updateCharacterEpisode(emptyList())
            }
        }
    }

    private fun updateCharacter(character: RickMortyCharacterUiModel) {
        _uiState.update { uiState ->
            uiState.copy(character = character)
        }
    }

    private fun updateCharacterEpisode(resource: List<RickMortyCharacterEpisode>) {
        _uiState.update { uiState ->
            uiState.copy(characterEpisode = resource)
        }
    }

    private fun updateEpisodeLoading(loadingValue: Boolean) {
        _uiState.update { uiState ->
            uiState.copy(isLoading = loadingValue)
        }
    }

    fun onClickFavorite() {
        if (isFavoriteCharacterState.value) {
            clearFavCharacter()
        } else {
            _uiState.value.character?.let { favCharacter ->
                putFavCharacter(favCharacter.asModel())
            }
        }
    }

    companion object {
        private const val CHARACTER_KEY = "character"
        private const val SECOND = 1_000L
        private const val DEFAULT_TIMEOUT_SECOND = 20 * SECOND
        private const val TIME_OUT_ERROR_MESSAGE = "시간초과 됐습니다."
    }
}

