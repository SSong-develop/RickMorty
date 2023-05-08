package com.ssong_develop.core_database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ssong_develop.core_database.model.LocalEntityRickMortyCharacter
import kotlinx.coroutines.flow.Flow

@Dao
interface RickMortyCharacterDao {

    @Query("SELECT * FROM rick_morty_characters_table")
    fun getCharacterPagingSource(): PagingSource<Int, LocalEntityRickMortyCharacter>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<LocalEntityRickMortyCharacter>)

    @Query("SELECT * FROM rick_morty_characters_table WHERE 1 = 1 LIMIT 1")
    suspend fun getRecentCharacter(): LocalEntityRickMortyCharacter

    @Delete
    suspend fun clearAllCharacters(characters: List<LocalEntityRickMortyCharacter>)
}