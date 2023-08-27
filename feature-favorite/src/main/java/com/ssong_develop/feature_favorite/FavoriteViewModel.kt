package com.ssong_develop.feature_favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssong_develop.core_common.WhileViewSubscribed
import com.ssong_develop.core_datastore.PreferenceStorage
import com.ssong_develop.core_model.RickMortyCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

internal sealed interface UiState {
    data class HasFavoriteCharacter(
        val favoriteCharacter: RickMortyCharacter
    ) : UiState

    object NoFavoriteCharacter : UiState
    object Loading : UiState
}

@ExperimentalCoroutinesApi
@HiltViewModel
internal class FavoriteViewModel @Inject constructor(
    private val preferenceStorage: PreferenceStorage
) : ViewModel() {

    private val hasFavoriteCharacterState: Flow<Pair<Boolean, RickMortyCharacter?>> =
        preferenceStorage.favoriteCharacter.map { favCharacter ->
            if (favCharacter != null) {
                true to favCharacter
            } else {
                false to null
            }
        }

    val uiState = hasFavoriteCharacterState.mapLatest {
        if (it.first) {
            UiState.HasFavoriteCharacter(favoriteCharacter = it.second!!)
        } else {
            UiState.NoFavoriteCharacter
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileViewSubscribed,
        initialValue = UiState.Loading
    )
}