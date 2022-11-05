package com.ssong_develop.core_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ssong_develop.core_model.RecentSearchKeyword
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentKeywordDao {

    @Insert
    suspend fun insertRecentKeyword(recentSearchKeyword: RecentSearchKeyword)

    @Delete
    suspend fun deleteRecentKeyword(keywordId: String)

    @Update
    suspend fun updateRecentKeyword(recentSearchKeyword: RecentSearchKeyword)

    @Query("SELECT * FROM recent_search_keyword")
    fun getAllRecentSearchKeywordStream(): Flow<RecentSearchKeyword>
}