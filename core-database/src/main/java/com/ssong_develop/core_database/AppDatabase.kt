package com.ssong_develop.core_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ssong_develop.core_model.Characters
import com.ssong_develop.core_database.converter.CharacterLocationListConverter
import com.ssong_develop.core_database.converter.OriginListConverter
import com.ssong_develop.core_database.converter.StringListConverter

@Database(
    entities = [Characters::class, RemoteKeys::class],
    version = 2,
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
    abstract fun remoteKeysDao(): RemoteKeysDao
}