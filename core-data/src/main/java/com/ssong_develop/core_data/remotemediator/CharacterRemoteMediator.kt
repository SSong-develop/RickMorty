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
import com.ssong_develop.core_model.RickMortyCharacter

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
                val remoteKey = database.withTransaction {
                    characterDao.getRecentCharacter().info.pages
                }
                remoteKey - 1
            }
            LoadType.APPEND -> {
                val remoteKey = database.withTransaction {
                    characterDao.getRecentCharacter().info.pages
                }
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

    companion object {
        private const val INITIAL_PAGE = 1
    }
}