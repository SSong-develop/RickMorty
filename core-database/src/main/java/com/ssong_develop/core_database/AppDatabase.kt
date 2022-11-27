package com.ssong_develop.core_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ssong_develop.core_database.converter.CharacterLocationListConverter
import com.ssong_develop.core_database.converter.OriginListConverter
import com.ssong_develop.core_database.converter.StringListConverter
import com.ssong_develop.core_model.Characters
import com.ssong_develop.core_model.RecentSearchKeyword

@Database(
    entities = [Characters::class, RecentSearchKeyword::class],
    version = 3,
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
    abstract fun recentSearchKeywordDao(): RecentKeywordDao
}