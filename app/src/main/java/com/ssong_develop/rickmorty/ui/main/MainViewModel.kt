package com.ssong_develop.rickmorty.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssong_develop.rickmorty.entities.Character
import com.ssong_develop.rickmorty.entities.WrapperCharacter
import com.ssong_develop.rickmorty.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    val toastLiveData: MutableLiveData<String> = MutableLiveData()

    /**
     * TODO : WrapperChracter를 받은 후에 각각 데이터를 넣어주는 방식으로 사용하면 될 거 같다. switchMap을 통해서 각각을 반환시켜주면 되는 부분이다.
     */
    private var _info = MutableLiveData<WrapperCharacter>()
    val info: LiveData<WrapperCharacter>
        get() = _info

    private var _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>>
        get() = _characters

    init {
        viewModelScope.launch {
            _characters = characterRepository.loadCharacter { toastLiveData.postValue(it) }
        }
    }
}