package com.ssong_develop.feature_character.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ssong_develop.core_data.repository.CharacterRepository
import com.ssong_develop.core_model.Characters
import com.ssong_develop.feature_character.delegate.FavoriteCharacterDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val favoriteCharacterDelegate: FavoriteCharacterDelegate
) : ViewModel(), FavoriteCharacterDelegate by favoriteCharacterDelegate {

    private val _characterUiEventBus = MutableSharedFlow<CharacterUiEvent>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val characterUiEventBus = _characterUiEventBus.asSharedFlow()

    private val _uiState = MutableStateFlow(CharacterUiState())
    val uiState = _uiState.asStateFlow()

    val pagingCharacterFlow: Flow<PagingData<Characters>> =
        characterRepository.getCharacterStream()
            .cachedIn(viewModelScope)

    fun updateLoadingState(loadingValue: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = loadingValue)
    }

    fun updateErrorState(errorValue: Boolean) {
        _uiState.value = _uiState.value.copy(isError = errorValue)
    }

    fun postRetryEvent() {
        _characterUiEventBus.tryEmit(CharacterUiEvent.Retry)
    }

    fun postRefreshEvent() {
        _characterUiEventBus.tryEmit(CharacterUiEvent.Refresh)
    }

    fun postFavoriteEvent() {
        if (favoriteCharacterState.value != null) {
            _characterUiEventBus.tryEmit(CharacterUiEvent.Favorite)
        }
    }

    fun postSearchEvent() {
        _characterUiEventBus.tryEmit(CharacterUiEvent.Search)
    }

    sealed interface CharacterUiEvent {
        object Retry : CharacterUiEvent
        object Refresh : CharacterUiEvent
        object Favorite : CharacterUiEvent
        object Search : CharacterUiEvent
    }
}

data class CharacterUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false
)