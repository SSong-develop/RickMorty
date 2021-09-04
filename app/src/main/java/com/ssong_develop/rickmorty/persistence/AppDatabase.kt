package com.ssong_develop.rickmorty.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ssong_develop.rickmorty.entities.Characters
import com.ssong_develop.rickmorty.entities.Episode
import com.ssong_develop.rickmorty.entities.Location

@Database(entities = [Characters::class, Episode::class, Location::class],version = 3, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao() : CharacterDao
    abstract fun episodeDao() : EpisodeDao
    abstract fun locationDao() : LocationDao
}