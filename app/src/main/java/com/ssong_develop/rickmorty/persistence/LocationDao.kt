package com.ssong_develop.rickmorty.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ssong_develop.rickmorty.entities.Location

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocationList(locations: List<Location>)

    @Query("SELECT * FROM location_table WHERE page = :page_")
    fun getLocations(page_: Int): List<Location>
}