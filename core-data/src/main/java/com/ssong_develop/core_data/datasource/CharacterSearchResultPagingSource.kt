package com.ssong_develop.core_data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ssong_develop.core_data.model.asModel
import com.ssong_develop.core_data.service.SearchService
import com.ssong_develop.core_model.RickMortyCharacter
import javax.inject.Inject

class CharacterSearchResultPagingSource @Inject constructor(
    private val searchService: SearchService,
    private val query: String
) : PagingSource<Int, RickMortyCharacter>() {

    override fun getRefreshKey(state: PagingState<Int, RickMortyCharacter>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RickMortyCharacter> {
        val currentKey = params.key ?: STARTING_KEY
        val prevKey = if (currentKey == STARTING_KEY) null else currentKey - 1

        val response = runCatching {
            searchService.searchCharacter(currentKey, query)
        }.mapCatching { networkResult ->
            networkResult.results.map { networkCharacter -> networkCharacter.asModel() }
        }.getOrElse { throwable ->
            return LoadResult.Error(throwable)
        }

        return if (response.isEmpty()) {
            LoadResult.Page(
                data = emptyList<RickMortyCharacter>(),
                prevKey = prevKey,
                nextKey = null
            )
        } else {
            LoadResult.Page(
                data = response,
                prevKey = prevKey,
                nextKey = currentKey + 1
            )
        }
    }

    companion object {
        private const val STARTING_KEY = 1
    }
}