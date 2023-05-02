package com.ssong_develop.core_database.spec

import androidx.room.AutoMigration
import androidx.room.RenameColumn
import androidx.room.RenameTable
import androidx.room.migration.AutoMigrationSpec

@RenameTable(
    fromTableName = "characters_table",
    toTableName = "rick_morty_characters_table"
)
class CharacterTableNameAutoMigration: AutoMigrationSpec {}

@RenameTable(
    fromTableName = "recent_search_keyword",
    toTableName = "rick_morty_recent_search_query_table"
)
class RecentSearchQueryTableNameAutoMigration: AutoMigrationSpec {}