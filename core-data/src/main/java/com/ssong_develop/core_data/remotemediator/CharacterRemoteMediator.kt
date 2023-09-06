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

/**
 * Paging and Cache in LocalDB
 *
 * @see (https://developer.android.com/topic/libraries/architecture/paging/v3-network-db)
 */
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
                val remoteKey = database.withTransaction { getPrevPageNumFromInfoURL() } ?: 1
                remoteKey - 1
            }

            LoadType.APPEND -> {
                val remoteKey = database.withTransaction { getNextPageNumFromInfoURL() }
                remoteKey + 1
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

    /**
     * get previous & next character page number function
     *
     * Server do not provide page number in Integer type, just Url type
     * For example
     * {
     *  ....
     *
     *  prev : https://rickmorty/character?page=2,
     *  next : https://rickmorty/character?page=4
     *
     *  ....
     * }
     * save the page url in DB and extract from url
     */
    private suspend fun getPrevPageNumFromInfoURL(): Int? =
        characterDao.getRecentCharacter().last().info.prev
            ?.split('/')
            ?.first() { it.contains(PAGE_QUERY_URI) }
            ?.replace(PAGE_QUERY_URI, "")
            ?.toInt()

    private suspend fun getNextPageNumFromInfoURL(): Int =
        characterDao.getRecentCharacter().last().info.next
            .split('/')
            .first { it.contains(PAGE_QUERY_URI) }
            .replace(PAGE_QUERY_URI, "")
            .toInt()

    companion object {
        private const val INITIAL_PAGE = 1
        private const val PAGE_QUERY_URI = "character?page="
    }
}