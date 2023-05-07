package com.ssong_develop.core_database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.ssong_develop.core_database.model.LocalEntityCharacters

@Dao
interface RickMortyCharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<LocalEntityCharacters>)
}