package com.ssong_develop.core_data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ssong_develop.core_data.model.NetworkRickMortyCharacter
import com.ssong_develop.core_data.service.CharacterService
import javax.inject.Inject

class CharacterPagingSource @Inject constructor(
    private val characterService: CharacterService
) : PagingSource<Int, NetworkRickMortyCharacter>() {

    override fun getRefreshKey(state: PagingState<Int, NetworkRickMortyCharacter>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NetworkRickMortyCharacter> {
        val currentKey = params.key ?: STARTING_KEY
        val prevKey = if (currentKey == STARTING_KEY) null else currentKey - 1

        val response = runCatching {
            characterService.getCharacters(currentKey)
        }.getOrElse { throwable ->
            return LoadResult.Error(throwable)
        }

        return if (response.results.isEmpty()) {
            LoadResult.Page(
                data = emptyList<NetworkRickMortyCharacter>(),
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

    companion object {
        private const val STARTING_KEY = 1
    }
}