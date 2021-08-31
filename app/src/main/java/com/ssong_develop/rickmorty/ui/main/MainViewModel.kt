package com.ssong_develop.rickmorty.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssong_develop.rickmorty.entities.Character
import com.ssong_develop.rickmorty.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    val toastLiveData: MutableLiveData<String> = MutableLiveData()

    private var _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>>
        get() = _characters

    init {
        _characters = characterRepository.loadCharacter { toastLiveData.postValue(it) }
    }
}