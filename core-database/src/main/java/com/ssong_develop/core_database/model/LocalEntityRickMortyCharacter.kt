package com.ssong_develop.core_database.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rick_morty_characters_table")
data class LocalEntityRickMortyCharacter(
    @PrimaryKey
    val id: Int,
    @Embedded
    val info: LocalEntityRickMortyCharacterInfo,
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

data class LocalEntityRickMortyCharacterInfo(
    @ColumnInfo("character_count")
    val count: Int,
    @ColumnInfo("character_pages")
    val pages: Int,
    @ColumnInfo("character_next_url")
    val next: String,
    @ColumnInfo("character_prev_url")
    val prev: String?
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
