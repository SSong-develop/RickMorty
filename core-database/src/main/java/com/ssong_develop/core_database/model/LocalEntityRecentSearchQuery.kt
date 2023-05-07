package com.ssong_develop.core_database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "rick_morty_recent_search_query_table")
data class LocalEntityRecentSearchQuery(
    @PrimaryKey
    @ColumnInfo("query_id")
    val queryId: String = UUID.randomUUID().toString(),
    @ColumnInfo("query")
    val query: String
)