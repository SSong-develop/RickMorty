package com.ssong_develop.rickmorty.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ssong_develop.rickmorty.converter.CharacterLocationListConverter
import com.ssong_develop.rickmorty.converter.OriginListConverter
import com.ssong_develop.rickmorty.converter.StringListConverter
import com.ssong_develop.rickmorty.entities.Characters

@Database(
    entities = [Characters::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    value = [
        CharacterLocationListConverter::class,
        OriginListConverter::class,
        StringListConverter::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao
}