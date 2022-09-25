package com.ssong_develop.core_data.network.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ssong_develop.core_data.network.service.CharacterService
import com.ssong_develop.core_model.Characters
import kotlinx.coroutines.delay
import javax.inject.Inject

class CharacterPagingSource @Inject constructor(
    private val characterService: CharacterService
) : PagingSource<Int, Characters>() {

    companion object {
        private const val STARTING_KEY = 1
    }

    override fun getRefreshKey(state: PagingState<Int, Characters>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Characters> {
        val currentKey = params.key ?: STARTING_KEY

        if (currentKey != STARTING_KEY) delay(500L)

        val response = runCatching {
            characterService.getCharacters(currentKey)
        }.getOrElse { throwable ->
            return LoadResult.Error(throwable)
        }

        val prevKey = if (currentKey == STARTING_KEY) null else currentKey - 1

        return runCatching {
            LoadResult.Page(
                data = response.results,
                prevKey = prevKey,
                nextKey = currentKey + 1
            )
        }.getOrElse { throwable ->
            LoadResult.Error(throwable)
        }
    }
}