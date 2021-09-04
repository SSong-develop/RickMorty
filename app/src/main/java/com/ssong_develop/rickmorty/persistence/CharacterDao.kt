package com.ssong_develop.rickmorty.persistence

import androidx.room.*
import com.ssong_develop.rickmorty.entities.Characters

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacterList(characters : List<Characters>)

    @Query("SELECT * FROM characters_table")
    fun getCharacters() : List<Characters>
}