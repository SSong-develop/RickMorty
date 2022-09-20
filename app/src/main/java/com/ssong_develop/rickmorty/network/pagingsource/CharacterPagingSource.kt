package com.ssong_develop.rickmorty.network.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ssong_develop.rickmorty.entities.Characters
import com.ssong_develop.rickmorty.network.service.CharacterService
import javax.inject.Inject

class CharacterPagingSource @Inject constructor(
    private val characterService: CharacterService
) : PagingSource<Int, List<Characters>>() {


    companion object {
        private const val STARTING_KEY = 1
    }

    override fun getRefreshKey(state: PagingState<Int, List<Characters>>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, List<Characters>> {
        val currentKey = params.key ?: STARTING_KEY

        val response = runCatching {
            characterService.getCharacters(currentKey)
        }.getOrElse { throwable ->
            return LoadResult.Error(throwable)
        }

        val prevKey = if (currentKey == STARTING_KEY) STARTING_KEY else currentKey - 1

        return runCatching {
            LoadResult.Page(
                data = requireNotNull(response.results) {"response is null"},
                prevKey = prevKey,
                nextKey = currentKey + 1
            )
        }.getOrElse { LoadResult.Error(it) }
    }

}