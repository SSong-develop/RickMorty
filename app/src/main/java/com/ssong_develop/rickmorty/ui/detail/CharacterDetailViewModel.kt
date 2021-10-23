package com.ssong_develop.rickmorty.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssong_develop.rickmorty.entities.Characters
import com.ssong_develop.rickmorty.entities.Episode
import com.ssong_develop.rickmorty.entities.getEpisodeNumbers
import com.ssong_develop.rickmorty.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import javax.inject.Inject

// TODO : SharedFlow를 어떻게 써야할 지 공부좀 해야할 거 같음
// TODO : 내가 궁금했던 거에 대한 답변을 찾을 수 있을 거 같아요!
@ExperimentalCoroutinesApi
@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    val toast = MutableLiveData<String>()

    val loading = MutableStateFlow(true)

    val character = MutableStateFlow(Characters())

    private val characterSharedFlow: MutableSharedFlow<Characters> = MutableSharedFlow(replay = 2)

    private val characterEpisodesFlow = characterSharedFlow.flatMapLatest { character ->
        repository.loadEpisodes(
            character.episode.getEpisodeNumbers(),
            onStart = { loading.value = true },
            onComplete = { loading.value = false },
            onError = {
                toast.postValue(it)
                loading.value = true
            }
        )
    }

    val characterEpisodeStateFlow = characterEpisodesFlow.stateIn(
        viewModelScope, WhileSubscribed(5000),
        emptyList()
    )

    fun postCharacter(character_: Characters) {
        characterSharedFlow.tryEmit(character_)
        character.value = character_
    }
}