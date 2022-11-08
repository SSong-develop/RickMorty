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
    private val savedStateHandle: SavedStateHandle,
    private val searchRepository: SearchRepository
) : ViewModel() {

    companion object {
        private const val SEARCH_QUERY = "search_query"
        private const val DEFAULT_DEBOUNCE_TIME = 500L
    }

    private val _searchUiEventBus = MutableSharedFlow<SearchUiEvent>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val searchUiEventBus = _searchUiEventBus.asSharedFlow()

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    // two-way DataBinding
    var searchQuery = MutableStateFlow(savedStateHandle.get(SEARCH_QUERY) ?: "")
        set(value) {
            savedStateHandle[SEARCH_QUERY] = value
            field = value
        }

    val resultStream: Flow<PagingData<Characters>> = searchQuery
        .debounce(DEFAULT_DEBOUNCE_TIME)
        .flatMapLatest { query -> searchRepository.getSearchResultStream(query) }
        .cachedIn(scope = viewModelScope)

    fun postShowToastEvent(message: String) {
        _searchUiEventBus.tryEmit(SearchUiEvent.ShowToast(message))
    }

    fun postRetryEvent() {
        _searchUiEventBus.tryEmit(SearchUiEvent.Retry)
    }

    fun postRefreshEvent() {
        _searchUiEventBus.tryEmit(SearchUiEvent.Refresh)
    }

    fun updateLoadingState(isLoading: Boolean) {
        _uiState.value = _uiState.value.copy(
            isLoading = isLoading
        )
    }

    fun updateErrorState(isError: Boolean) {
        _uiState.value = _uiState.value.copy(
            isError = isError
        )
    }

    sealed interface SearchUiEvent {
        data class ShowToast(val message: String) : SearchUiEvent
        object Retry: SearchUiEvent
        object Refresh: SearchUiEvent
    }
}

data class SearchUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false
)