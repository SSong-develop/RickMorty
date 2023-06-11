package com.ssong_develop.feature_character.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.ssong_develop.core_data.model.asModel
import com.ssong_develop.core_data.repository.CharacterRepository
import com.ssong_develop.feature_character.delegate.FavoriteCharacterDelegate
import com.ssong_develop.feature_character.model.RickMortyCharacterUiModel
import com.ssong_develop.feature_character.model.asUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
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

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@HiltViewModel
class CharacterViewModel @Inject constructor(
    characterRepository: CharacterRepository,
    private val favoriteCharacterDelegate: FavoriteCharacterDelegate
) : ViewModel(), FavoriteCharacterDelegate by favoriteCharacterDelegate {

    private val _characterEventBus = MutableSharedFlow<CharacterEvent>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val characterUiEventBus = _characterEventBus.asSharedFlow()

    private val _uiState = MutableStateFlow(CharacterUiState())
    val uiState = _uiState.asStateFlow()

    val localRickMortyCharacterPagingStream: Flow<PagingData<RickMortyCharacterUiModel>> =
        characterRepository
            .databaseCharacterStream()
            .map { pagingdata -> pagingdata.map { model -> model.asModel().asUiModel() } }
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
