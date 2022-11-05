package com.ssong_develop.feature_character.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssong_develop.core_common.Resource
import com.ssong_develop.core_common.WhileViewSubscribed
import com.ssong_develop.core_data.repository.CharacterRepository
import com.ssong_develop.core_model.Characters
import com.ssong_develop.core_model.Episode
import com.ssong_develop.feature_character.delegate.FavoriteCharacterDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import javax.inject.Inject

// TODO 이 뷰를 재활용 한다
@ExperimentalCoroutinesApi
@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: CharacterRepository,
    private val favoriteCharacterDelegate: FavoriteCharacterDelegate
) : ViewModel(), FavoriteCharacterDelegate by favoriteCharacterDelegate {

    private val _uiEventState = MutableSharedFlow<DetailUiEvent>(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val uiEventState = _uiEventState.asSharedFlow()

    // todo 이건 스트림으로 받는 것이 이상하다
    // todo 시간의 흐름에 따라 바뀌는 것을 받거나 그런 것들에만 되어야 하는 녀석인데 그러지 않기 때문이다.
    val characterState = savedStateHandle.getStateFlow<Characters?>("character", null)

    val characterEpisodesFlow: StateFlow<Resource<List<Episode>>> =
        characterState
            .flatMapLatest { character ->
                character?.let {
                    repository.getEpisodes(it.episode)
                } ?: flowOf(Resource.error("character is null", emptyList()))
            }
            .stateIn(
                scope = viewModelScope,
                started = WhileViewSubscribed,
                initialValue = Resource.loading(emptyList())
            )

    val isFavoriteCharacterStateFlow: StateFlow<Boolean> = combine(
        characterState,
        favoriteCharacterState
    ) { selectCharacter, favCharacter ->
        if (favCharacter == null || selectCharacter == null) {
            false
        } else {
            favCharacter.id == selectCharacter.id
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileViewSubscribed,
        initialValue = false
    )

    fun postBackEvent() {
        _uiEventState.tryEmit(DetailUiEvent.Back)
    }

    fun onClickFavorite() {
        if (isFavoriteCharacterStateFlow.value) {
            clearFavCharacter()
        } else {
            characterState.value?.let { favCharacter ->
                putFavCharacter(favCharacter)
            }
        }
    }

    sealed interface DetailUiEvent {
        object Back : DetailUiEvent
    }
}