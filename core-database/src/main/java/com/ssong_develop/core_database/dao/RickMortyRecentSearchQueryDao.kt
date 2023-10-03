package com.ssong_develop.core_database.dao

import androidx.room.*
import com.ssong_develop.core_database.model.LocalEntityRecentSearchQuery
import kotlinx.coroutines.flow.Flow

@Dao
interface RickMortyRecentSearchQueryDao {

    @Insert
    fun insertRecentKeyword(entity: LocalEntityRecentSearchQuery)

    @Delete
    fun deleteRecentKeyword(entity: LocalEntityRecentSearchQuery)

    @Update
    fun updateRecentKeyword(entity: LocalEntityRecentSearchQuery)

    @Query("SELECT * FROM rick_morty_recent_search_query_table")
    fun recentSearchQueryStream(): Flow<LocalEntityRecentSearchQuery>
}