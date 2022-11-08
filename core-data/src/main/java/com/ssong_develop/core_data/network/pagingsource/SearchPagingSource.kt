package com.ssong_develop.core_data.network.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ssong_develop.core_data.network.service.SearchService
import com.ssong_develop.core_model.Characters
import javax.inject.Inject

class SearchPagingSource @Inject constructor(
    private val searchService: SearchService,
    private val query: String
) : PagingSource<Int, Characters>() {
    companion object {
        private const val STARTING_KEY = 1
    }

    override fun getRefreshKey(state: PagingState<Int, Characters>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Characters> {
        val currentKey = params.key ?: STARTING_KEY

        val response = runCatching {
            searchService.searchCharacter(currentKey, query)
        }.getOrElse { throwable ->
            return LoadResult.Error(throwable)
        }

        val prevKey = if (currentKey == STARTING_KEY) null else currentKey - 1

        return if (response.results.isEmpty()) {
            LoadResult.Page(
                data = emptyList<Characters>(),
                prevKey = prevKey,
                nextKey = null
            )
        } else {
            LoadResult.Page(
                data = response.results,
                prevKey = prevKey,
                nextKey = currentKey + 1
            )
        }
    }
}