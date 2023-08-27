package com.ssong_develop.core_data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.ssong_develop.core_data.datasource.CharacterSearchResultPagingSource
import com.ssong_develop.core_data.service.SearchService
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val service: SearchService
) {

    fun getCharacterSearchResultStream(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = INITIAL_PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                CharacterSearchResultPagingSource(
                    searchService = service,
                    query = query
                )
            }
        ).flow

    companion object {
        private const val INITIAL_PAGE_SIZE = 10
    }
}