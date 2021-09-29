package com.ssong_develop.rickmorty.ui.theme.character

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssong_develop.rickmorty.entities.Characters
import com.ssong_develop.rickmorty.repository.CharacterRepository
import com.ssong_develop.rickmorty.ui.LiveCoroutinesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {
    private val toastLiveData: MutableLiveData<String> = MutableLiveData()

    private val characterPage: MutableStateFlow<Int> = MutableStateFlow(0)

    @ExperimentalCoroutinesApi
    val characters: Flow<List<Characters>> = characterPage.flatMapLatest { page ->
        characterRepository.loadCharacters(page) { toastLiveData.postValue(it) }
    }.catch {
        toastLiveData.postValue(it.toString())
    }.flowOn(Dispatchers.Main)

    @ExperimentalCoroutinesApi
    val loading: Flow<Boolean> = characters.map {
        it.isEmpty()
    }

    fun morePage() {
        characterPage.value += 1
    }
}