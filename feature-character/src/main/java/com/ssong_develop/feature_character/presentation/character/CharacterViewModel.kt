package com.ssong_develop.feature_character.presentation.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.ssong_develop.core_data.repository.CharacterRepository
import com.ssong_develop.feature_character.delegate.FavoriteCharacterDelegate
import com.ssong_develop.feature_character.model.RickMortyCharacterUiModel
import com.ssong_develop.feature_character.model.asUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

sealed interface CharacterUiState {
    object Error : CharacterUiState
    object Loading : CharacterUiState
    data class Characters(
        val favoriteCharacter: RickMortyCharacterUiModel? = null
    ) : CharacterUiState
}

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@HiltViewModel
class CharacterViewModel @Inject constructor(
    characterRepository: CharacterRepository,
    favoriteCharacterDelegate: FavoriteCharacterDelegate
) : ViewModel(), FavoriteCharacterDelegate by favoriteCharacterDelegate {

    private val _uiState: MutableStateFlow<CharacterUiState> =
        MutableStateFlow(CharacterUiState.Loading)
    val uiState = _uiState.asStateFlow()

    val networkRickMortyCharacterPagingStream: Flow<PagingData<RickMortyCharacterUiModel>> =
        characterRepository
            .getCharacterStream()
            .map { pagingData -> pagingData.map { model -> model.asUiModel() } }
            .cachedIn(viewModelScope)

    fun updateUiState(uiState: CharacterUiState) {
        _uiState.value = uiState
    }

    fun updateFavoriteCharacter(favoriteCharacter: RickMortyCharacterUiModel) {
        if (_uiState.value is CharacterUiState.Characters) {
            _uiState.update { uiState ->
                (uiState as CharacterUiState.Characters).copy(favoriteCharacter = favoriteCharacter)
            }
        }
    }
}
