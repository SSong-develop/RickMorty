package com.ssong_develop.feature_character.presentation.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.ssong_develop.core_data.repository.CharacterRepository
import com.ssong_develop.core_datastore.PreferenceStorage
import com.ssong_develop.feature_character.model.RickMortyCharacterUiModel
import com.ssong_develop.feature_character.model.mapper.asUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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
    preferenceStorage: PreferenceStorage
) : ViewModel() {

    private val _uiState: MutableStateFlow<CharacterUiState> =
        MutableStateFlow(CharacterUiState.Loading)
    val uiState = _uiState.asStateFlow()

    val favoriteCharacterState =
        preferenceStorage.favoriteCharacter.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = null
        )

    val networkRickMortyCharacterPagingStream: Flow<PagingData<RickMortyCharacterUiModel>> =
        characterRepository
            .getCharacterStream()
            .map { pagingData -> pagingData.map { model -> model.asUiModel() } }
            .cachedIn(viewModelScope)

    fun updateUiState(uiState: CharacterUiState) {
        _uiState.value = uiState
    }
}
