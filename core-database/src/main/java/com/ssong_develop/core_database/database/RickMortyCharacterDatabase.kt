package com.ssong_develop.core_database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ssong_develop.core_database.converter.CharacterLocationListConverter
import com.ssong_develop.core_database.converter.OriginListConverter
import com.ssong_develop.core_database.converter.StringListConverter
import com.ssong_develop.core_database.dao.RickMortyCharacterDao
import com.ssong_develop.core_database.dao.RickMortyRecentSearchQueryDao
import com.ssong_develop.core_database.model.LocalEntityRecentSearchQuery
import com.ssong_develop.core_database.model.LocalEntityRickMortyCharacter

@Database(
    entities = [LocalEntityRickMortyCharacter::class, LocalEntityRecentSearchQuery::class],
    version = 3
)
@TypeConverters(
    value = [
        CharacterLocationListConverter::class,
        OriginListConverter::class,
        StringListConverter::class
    ]
)
abstract class RickMortyCharacterDatabase : RoomDatabase() {
    abstract fun characterDao(): RickMortyCharacterDao
    abstract fun recentSearchKeywordDao(): RickMortyRecentSearchQueryDao
}