package com.ssong_develop.rickmorty.ui.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssong_develop.rickmorty.entities.Characters
import com.ssong_develop.rickmorty.entities.getEpisodeNumber
import com.ssong_develop.rickmorty.entities.getEpisodeNumbers
import com.ssong_develop.rickmorty.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    val toast = MutableLiveData<String>()

    val loading = MutableStateFlow(true)

    val character = MutableStateFlow(Characters())

    val characterEpisodesFlow = character.flatMapLatest { character ->
        repository.loadEpisodes(
            character.episode[0].getEpisodeNumber(),
            onStart = { loading.value = true },
            onComplete = { loading.value = false },
            onError = {
                toast.postValue(it)
                loading.value = true
            }
        )
    }

    fun postCharacter(character_: Characters) {
        character.value = character_
    }
}