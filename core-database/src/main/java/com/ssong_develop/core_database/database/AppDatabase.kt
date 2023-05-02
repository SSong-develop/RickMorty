package com.ssong_develop.core_database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ssong_develop.core_database.converter.CharacterLocationListConverter
import com.ssong_develop.core_database.converter.OriginListConverter
import com.ssong_develop.core_database.converter.StringListConverter
import com.ssong_develop.core_database.dao.RickMortyCharacterDao
import com.ssong_develop.core_database.dao.RickMortyRecentSearchQueryDao
import com.ssong_develop.core_database.entity.LocalEntityCharacters
import com.ssong_develop.core_database.entity.LocalEntityRecentSearchQuery

@Database(
    entities = [LocalEntityCharacters::class, LocalEntityRecentSearchQuery::class],
    version = 2,
    exportSchema = true
)
@TypeConverters(
    value = [
        CharacterLocationListConverter::class,
        OriginListConverter::class,
        StringListConverter::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao(): RickMortyCharacterDao
    abstract fun recentSearchKeywordDao(): RickMortyRecentSearchQueryDao
}