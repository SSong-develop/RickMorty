package com.ssong_develop.core_data.remotemediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.ssong_develop.core_data.service.CharacterService
import com.ssong_develop.core_database.database.AppDatabase
import com.ssong_develop.core_model.RickMortyCharacter

@ExperimentalPagingApi
class CharacterRemoteMediator(
    private val database: AppDatabase,
    private val service: CharacterService
) : RemoteMediator<Int, RickMortyCharacter>() {
    // return try {
    //            val page = when (loadType) {
    //                LoadType.REFRESH -> 1 // 처음 페이지 로드 시
    //                LoadType.PREPEND -> { // 이전 페이지 로드 시
    //                    val prevKey = state.firstOrNull()?.id ?: return MediatorResult.Success(
    //                        endOfPaginationReached = true
    //                    )
    //                    prevKey - 1
    //                }
    //                LoadType.APPEND -> { // 다음 페이지 로드 시
    //                    val nextKey = state.lastOrNull()?.id ?: return MediatorResult.Success(
    //                        endOfPaginationReached = true
    //                    )
    //                    nextKey + 1
    //                }
    //            }
    //
    //            val response = api.getData(page) // API에서 데이터 가져오기
    //            val data = response.items // 가져온 데이터
    //
    //            // 가져온 데이터를 로컬 데이터베이스에 저장
    //            if (loadType == LoadType.REFRESH) {
    //                database.clearAllTables()
    //            }
    //            database.dataDao().insertAll(data)
    //
    //            MediatorResult.Success(endOfPaginationReached = response.items.isEmpty())
    //        } catch (e: Exception) {
    //            MediatorResult.Error(e)
    //        }
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RickMortyCharacter>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> {
                    val prevKey = state.firstItemOrNull()?.id ?: return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                    prevKey - 1
                }
                LoadType.APPEND -> {
                    val nextKey = state.lastItemOrNull()?.id ?: return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                    nextKey + 1
                }
            }

            val response = service.getCharacters(page = page)
            val data = response.results

            if (loadType == LoadType.REFRESH) {
                database.clearAllTables()
            }
            database.characterDao().insertCharacters(data)
        } catch (exception: Exception) {

        }
    }

}