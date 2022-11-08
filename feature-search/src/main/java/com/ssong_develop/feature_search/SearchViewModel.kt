package com.ssong_develop.feature_search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ssong_develop.core_data.repository.SearchRepository
import com.ssong_develop.core_model.Characters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class SearchViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _searchUiEventBus = MutableSharedFlow<SearchUiEvent>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val searchUiEventBus = _searchUiEventBus.asSharedFlow()

    // two-way DataBinding
    var searchQuery = MutableStateFlow("")

    val resultStream: Flow<PagingData<Characters>> = searchQuery
        .debounce(500L)
        .flatMapLatest { query -> searchRepository.getSearchResultStream(query) }
        .cachedIn(scope = viewModelScope)

    fun postShowToastEvent(message: String) {
        _searchUiEventBus.tryEmit(SearchUiEvent.ShowToast(message))
    }

    sealed interface SearchUiEvent {
        data class ShowToast(val message: String) : SearchUiEvent
    }

}