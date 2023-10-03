package com.ssong_develop.core_database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ssong_develop.core_database.model.LocalEntityRickMortyCharacter

@Dao
interface RickMortyCharacterDao {

    @Query("SELECT * FROM rick_morty_characters_table")
    fun getCharacterPagingSource(): PagingSource<Int, LocalEntityRickMortyCharacter>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacters(characters: List<LocalEntityRickMortyCharacter>)

    @Query("SELECT * FROM rick_morty_characters_table")
    fun getRecentCharacter(): List<LocalEntityRickMortyCharacter>

    @Delete
    fun clearAllCharacters(characters: List<LocalEntityRickMortyCharacter>)
}