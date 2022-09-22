package com.ssong_develop.rickmorty.ui.character

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ssong_develop.rickmorty.entities.Characters
import com.ssong_develop.rickmorty.persistence.datastore.RickMortyDataStore
import com.ssong_develop.rickmorty.repository.CharacterRepository
import com.ssong_develop.rickmorty.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val rickMortyDataStore: RickMortyDataStore
) : ViewModel(),
    RickMortyDataStore by rickMortyDataStore {

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

    val pagingCharacterFlow: Flow<PagingData<Characters>> = characterPage.flatMapLatest {
        Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            initialKey = it,
            pagingSourceFactory = { characterRepository.charactersPagingSource() }
        ).flow.cachedIn(viewModelScope)
    }

    val favoriteCharacter = favoriteCharacterIdFlow
        .filterNotNull()
        .filter { id -> id != -2 }
        .map { id ->
            characterRepository.getCharacter(id)
        }.stateIn(
            scope = viewModelScope,
            started = Eagerly,
            initialValue = Resource.loading(null)
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