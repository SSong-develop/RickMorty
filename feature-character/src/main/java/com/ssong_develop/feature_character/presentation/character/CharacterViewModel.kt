package com.ssong_develop.feature_character.presentation.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.ssong_develop.core_common.WhileViewSubscribed
import com.ssong_develop.core_data.repository.CharacterRepository
import com.ssong_develop.core_datastore.PreferenceStorage
import com.ssong_develop.feature_character.model.RickMortyCharacterUiModel
import com.ssong_develop.feature_character.model.mapper.asUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed interface UiState {
    data object Error : UiState
    data object Loading : UiState
    data class Characters(
        val favoriteCharacter: RickMortyCharacterUiModel? = null
    ) : UiState
}

@ExperimentalPagingApi
@HiltViewModel
internal class CharacterViewModel @Inject constructor(
    characterRepository: CharacterRepository,
    preferenceStorage: PreferenceStorage
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    val favoriteCharacterState =
        preferenceStorage.favoriteCharacter.stateIn(
            scope = viewModelScope,
            started = WhileViewSubscribed,
            initialValue = null
        )

    val networkRickMortyCharacterPagingStream: Flow<PagingData<RickMortyCharacterUiModel>> =
        characterRepository.networkCharacterStream()
            .map { pagingData ->
                pagingData.map { model -> model.asUiModel() }
            }
            .cachedIn(viewModelScope)

    fun updateUiState(uiState: UiState) {
        _uiState.value = uiState
    }
}
