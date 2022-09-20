package com.ssong_develop.rickmorty.ui.character

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssong_develop.rickmorty.entities.Characters
import com.ssong_develop.rickmorty.repository.CharacterRepository
import com.ssong_develop.rickmorty.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    val toastMessage: MutableLiveData<String> = MutableLiveData()

    private val maxPage = MutableStateFlow(0)

    private val characterPage: MutableStateFlow<Int> = MutableStateFlow(1)

    val characterFlow: StateFlow<Resource<List<Characters>>> =
        characterPage.flatMapLatest { page ->
            characterRepository.loadCharacters(
                page = page,
                onFetchFailed = {
                    toastMessage.postValue(it)
                }
            )
        }.stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5000),
            initialValue = Resource(Resource.Status.LOADING, emptyList(), null)
        )

    fun morePage() {
        characterPage.value++
    }

    fun resetPage() {
        characterPage.value = 1
    }

    fun onLastPage(): Boolean {
        return maxPage.value == characterPage.value
    }
}