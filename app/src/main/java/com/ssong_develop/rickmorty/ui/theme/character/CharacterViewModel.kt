package com.ssong_develop.rickmorty.ui.theme.character

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.switchMap
import com.ssong_develop.rickmorty.entities.Characters
import com.ssong_develop.rickmorty.repository.CharacterRepository
import com.ssong_develop.rickmorty.ui.LiveCoroutinesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : LiveCoroutinesViewModel() {

    private val toastLiveData: MutableLiveData<String> = MutableLiveData()

    private val characterPageLiveData: MutableLiveData<Int> = MutableLiveData(0)
    val characters: LiveData<List<Characters>> = characterPageLiveData.switchMap { page ->
        launchOnViewModelScope {
            characterRepository.loadCharacters(page) { toastLiveData.postValue(it) }
        }
    }

    val loading: LiveData<Boolean> = Transformations.map(characters) {
        it.isEmpty()
    }

    fun initialFetchCharacters(value: Int) {
        characterPageLiveData.value = value
    }

    fun morePage() {
        characterPageLiveData.value = characterPageLiveData.value!!.plus(1)
    }
}