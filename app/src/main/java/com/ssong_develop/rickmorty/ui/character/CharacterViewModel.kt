package com.ssong_develop.rickmorty.ui.character

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssong_develop.rickmorty.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel(), LifecycleObserver {

    val toastMessage: MutableLiveData<String> = MutableLiveData()

    val loading = MutableStateFlow(true)

    private val characterPage: MutableStateFlow<Int> = MutableStateFlow(1)

    @ExperimentalCoroutinesApi
    private val charactersFlow = characterPage.flatMapLatest { page ->
        characterRepository.loadCharacters(
            page = page,
            onStart = { loading.value = true },
            onComplete = { loading.value = false },
            onError = {
                loading.value = true
                toastMessage.postValue(it)
            }
        )
    }

    @ExperimentalCoroutinesApi
    val characterStateFlow = charactersFlow.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun morePage() {
        characterPage.value += 1
    }

    fun refreshPage() {
        characterPage.value = 1
    }
}