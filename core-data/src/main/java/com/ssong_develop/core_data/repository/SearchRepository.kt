package com.ssong_develop.core_data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.ssong_develop.core_common.di.IoDispatcher
import com.ssong_develop.core_data.network.pagingsource.SearchPagingSource
import com.ssong_develop.core_data.network.service.SearchService
import com.ssong_develop.rickmorty.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val service: SearchService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : Repository {

    companion object {
        private const val INITIAL_PAGE_SIZE = 10
    }

    fun getSearchResultStream(query: String) = Pager(
        config = PagingConfig(pageSize = INITIAL_PAGE_SIZE, enablePlaceholders = true),
        pagingSourceFactory = { SearchPagingSource(service, query) }
    ).flow.flowOn(ioDispatcher)
}