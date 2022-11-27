package com.ssong_develop.core_model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "recent_search_keyword")
data class RecentSearchKeyword(
    @PrimaryKey
    val keywordId: String = UUID.randomUUID().toString(),
    val keyword: String
)