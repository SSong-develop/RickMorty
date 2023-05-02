package com.ssong_develop.core_database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rick_morty_characters_table")
data class LocalEntityCharacters(
    @PrimaryKey
    val id: Int,
    @ColumnInfo("character_name")
    val name: String,
    @ColumnInfo("character_status")
    val status: String,
    @ColumnInfo("character_species")
    val species: String,
    @ColumnInfo("character_type")
    val type: String,
    @ColumnInfo("character_gender")
    val gender: String,
    @Embedded
    val origin: LocalEntityOrigin,
    @Embedded
    val location: LocalEntityLocation,
    @ColumnInfo("character_image_url")
    val image: String,
    @ColumnInfo("character_episodes")
    val episode: List<String>,
    @ColumnInfo("character_about_url")
    val url: String,
    @ColumnInfo("character_created_date")
    val created: String
)

data class LocalEntityOrigin(
    @ColumnInfo("origin_name")
    val name: String,
    @ColumnInfo("origin_url")
    val url: String
)

data class LocalEntityLocation(
    @ColumnInfo("location_name")
    val name: String,
    @ColumnInfo("location_url")
    val url: String
)
