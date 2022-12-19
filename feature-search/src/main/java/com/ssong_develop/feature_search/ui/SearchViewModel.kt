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

sealed interface SearchEvent {
    data class ShowToast(val message: String) : SearchEvent
    object Retry : SearchEvent
    object Refresh : SearchEvent
}

data class SearchUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false
)

// TODO state 다시 확인하기
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

    private val _searchEventBus = MutableSharedFlow<SearchEvent>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val searchUiEventBus = _searchEventBus.asSharedFlow()

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    // two-way DataBinding
    // TODO 이것도 STATE에 들어가야할 거 같은데..아 될거 같음 ㅇㅇㅇㅇㅇㅇ
    var searchQuery = MutableStateFlow(savedStateHandle[SEARCH_QUERY] ?: "")
        set(value) {
            field = value
            savedStateHandle[SEARCH_QUERY] = value
        }

    val searchResultStream: Flow<PagingData<Characters>> = searchQuery
        .filter { query -> query.isNotEmpty() }
        .debounce(DEFAULT_DEBOUNCE_TIME)
        .flatMapLatest { query -> searchRepository.getSearchResultStream(query) }
        .cachedIn(scope = viewModelScope)

    fun updateLoadingState(isLoading: Boolean) {
        _uiState.update { state ->
            state.copy(
                isLoading = isLoading
            )
        }
    }

    fun updateErrorState(isError: Boolean) {
        _uiState.update { state ->
            state.copy(
                isError = isError
            )
        }
    }
}
