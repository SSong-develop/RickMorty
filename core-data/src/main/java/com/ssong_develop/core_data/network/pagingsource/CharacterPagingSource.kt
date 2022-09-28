package com.ssong_develop.core_data.network.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ssong_develop.core_data.network.service.CharacterService
import com.ssong_develop.core_model.Characters
import kotlinx.coroutines.delay
import javax.inject.Inject

// TODO( api가 디자인을 url로 디자인을 해서 url로 페이징을 하는게 좋아보입니다. )
class CharacterPagingSource @Inject constructor(
    private val characterService: CharacterService
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
            characterService.getCharacters(currentKey)
        }.getOrElse { throwable ->
            return LoadResult.Error(throwable)
        }

        val prevKey = if (currentKey == STARTING_KEY) null else currentKey - 1

        // next로 다음에 볼 수 있는 url을 넘겨줍니다.
        // url중 page 변수만 찾아다가 하면 될 거 같은데요??

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