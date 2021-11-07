package com.ssong_develop.rickmorty.ui.character

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssong_develop.rickmorty.entities.Characters
import com.ssong_develop.rickmorty.network.Resource
import com.ssong_develop.rickmorty.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    // FIXME : Test value for initialize instance
    @VisibleForTesting
    val testValue = 1

    val toastMessage: MutableLiveData<String> = MutableLiveData()

    private val characterPage: MutableStateFlow<Int> = MutableStateFlow(1)

    private val characterFlow: Flow<Resource<List<Characters>>> =
        characterPage.flatMapLatest { page ->
            characterRepository.loadCharacters(
                page = page
            )
        }

    val characterStateFlow: StateFlow<Resource<List<Characters>>> = characterFlow.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = Resource(Resource.Status.LOADING, emptyList(), null)
    )

    fun morePage() {
        characterPage.value++
    }

    fun refreshPage() {
        characterPage.value = 1
    }
}