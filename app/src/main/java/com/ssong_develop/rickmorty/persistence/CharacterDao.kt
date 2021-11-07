package com.ssong_develop.rickmorty.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ssong_develop.rickmorty.entities.Characters
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacterList(characters: List<Characters>)

    @Query("SELECT * FROM characters_table WHERE page = :page_")
    fun getCharacters(page_: Int): Flow<List<Characters>>
}