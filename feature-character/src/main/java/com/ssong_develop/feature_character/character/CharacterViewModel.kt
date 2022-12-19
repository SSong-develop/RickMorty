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

data class CharacterUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false
)

sealed class CharacterEvent {
    object Retry : CharacterEvent()
    object Refresh : CharacterEvent()
    object Favorite : CharacterEvent()
    object Search : CharacterEvent()
}

@ExperimentalCoroutinesApi
@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val favoriteCharacterDelegate: FavoriteCharacterDelegate
) : ViewModel(), FavoriteCharacterDelegate by favoriteCharacterDelegate {

    private val _characterEventBus = MutableSharedFlow<CharacterEvent>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val characterUiEventBus = _characterEventBus.asSharedFlow()

    private val _uiState = MutableStateFlow(CharacterUiState())
    val uiState = _uiState.asStateFlow()

    val characterStream: Flow<PagingData<Characters>> =
        characterRepository.getCharacterStream()
            .cachedIn(viewModelScope)

    fun updateLoadingState(loadingValue: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = loadingValue)
    }

    fun updateErrorState(errorValue: Boolean) {
        _uiState.value = _uiState.value.copy(isError = errorValue)
    }

    fun postRetryEvent() {
        _characterEventBus.tryEmit(CharacterEvent.Retry)
    }

    fun postRefreshEvent() {
        _characterEventBus.tryEmit(CharacterEvent.Refresh)
    }

    fun postFavoriteEvent() {
        if (favoriteCharacterState.value != null) {
            _characterEventBus.tryEmit(CharacterEvent.Favorite)
        }
    }

    fun postSearchEvent() {
        _characterEventBus.tryEmit(CharacterEvent.Search)
    }
}
