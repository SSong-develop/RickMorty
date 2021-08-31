package com.ssong_develop.rickmorty.ui.main

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.ssong_develop.rickmorty.entities.Character
import com.ssong_develop.rickmorty.repository.CharacterRepository
import com.ssong_develop.rickmorty.ui.LiveCoroutinesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : LiveCoroutinesViewModel() {

    var testValue = 1

    val toastLiveData: MutableLiveData<String> = MutableLiveData()

    private val characterPageLiveData: MutableLiveData<Int> = MutableLiveData()
    val characters: LiveData<List<Character>>

    init {
        characters = characterPageLiveData.switchMap { page ->
            launchOnViewModelScope {
                characterRepository.loadCharacters(page) { toastLiveData.postValue(it) }
            }
        }
    }

    fun isLoading() = characterRepository.isLoading

    @MainThread
    fun refresh() {
        characterPageLiveData.value = testValue
        testValue++
    }
}