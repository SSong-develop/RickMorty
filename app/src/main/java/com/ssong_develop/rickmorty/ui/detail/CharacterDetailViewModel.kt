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

@ExperimentalCoroutinesApi
@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {
    val toast = MutableLiveData<String>()

    val loading = MutableStateFlow(true)

    val character = MutableStateFlow(Characters())

    private val characterEpisodeSharedFlow: MutableSharedFlow<List<String>> =
        MutableSharedFlow(replay = 1)

    private val characterEpisodesFlow: Flow<List<Episode>> =
        characterEpisodeSharedFlow.flatMapLatest { episode ->
            episode.run {
                repository.loadEpisodes(
                    episode.getEpisodeNumbers(),
                    onStart = { loading.value = true },
                    onComplete = { loading.value = false },
                    onError = {
                        loading.value = true
                        toast.postValue(it)
                    }
                )
            }
        }

    val characterEpisodeStateFlow: StateFlow<List<Episode>> = characterEpisodesFlow.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun postCharacter(character_: Characters) {
        characterEpisodeSharedFlow.tryEmit(character_.episode)
        character.value = character_
    }
}