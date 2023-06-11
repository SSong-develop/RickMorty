package com.ssong_develop.core_data.remotemediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ssong_develop.core_data.model.asLocalEntity
import com.ssong_develop.core_data.service.CharacterService
import com.ssong_develop.core_database.dao.RickMortyCharacterDao
import com.ssong_develop.core_database.database.RickMortyCharacterDatabase
import com.ssong_develop.core_database.model.LocalEntityRickMortyCharacter

@ExperimentalPagingApi
class CharacterRemoteMediator(
    private val database: RickMortyCharacterDatabase,
    private val service: CharacterService
) : RemoteMediator<Int, LocalEntityRickMortyCharacter>() {

    private val characterDao: RickMortyCharacterDao = database.characterDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocalEntityRickMortyCharacter>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> INITIAL_PAGE
            LoadType.PREPEND -> {
                database.withTransaction {
                    characterDao.getRecentCharacter().last().info.next
                        .split('/')
                        .first { it.contains(PAGE_QUERY_URI) }
                        .replace(PAGE_QUERY_URI, "")
                        .toInt()
                }
            }
            LoadType.APPEND -> {
                database.withTransaction {
                    characterDao.getRecentCharacter().last().info.prev
                        ?.split('/')
                        ?.first() { it.contains(PAGE_QUERY_URI) }
                        ?.replace(PAGE_QUERY_URI, "")
                        ?.toInt()
                } ?: 1
            }
        }

        val data = runCatching {
            service.getCharacters(page = page)
        }.getOrElse { throwable ->
            return MediatorResult.Error(throwable)
        }

        database.withTransaction {
            val localEntityCharacters = data.results.map { it.asLocalEntity(data.info) }
            if (loadType == LoadType.REFRESH) {
                characterDao.clearAllCharacters(localEntityCharacters)
            }
            characterDao.insertCharacters(localEntityCharacters)
        }
        return MediatorResult.Success(endOfPaginationReached = data.results.isEmpty())
    }

    companion object {
        private const val INITIAL_PAGE = 1

        private const val PAGE_QUERY_URI = "character?page="
    }
}