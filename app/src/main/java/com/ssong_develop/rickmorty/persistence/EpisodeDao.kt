package com.ssong_develop.rickmorty.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ssong_develop.rickmorty.entities.Episode

@Dao
interface EpisodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEpisodeList(episodes: List<Episode>)

    @Query("SELECT * FROM episode_table WHERE page = :page_")
    fun getEpisodes(page_: Int): List<Episode>
}