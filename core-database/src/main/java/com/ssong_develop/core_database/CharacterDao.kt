package com.ssong_develop.core_database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ssong_develop.core_model.Characters
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<Characters>)

    @Query("SELECT * FROM characters_table WHERE page = :page_")
    fun getCharacters(page_: Int): Flow<List<Characters>>

    @Query("SELECT * FROM characters_table WHERE page = :page_ " +
            "ORDER BY id DESC")
    fun getCharactersWithPaging(page_: Int): PagingSource<Int,Characters>

    @Query("DELETE FROM characters_table")
    suspend fun clearCharacterDatabase()
}