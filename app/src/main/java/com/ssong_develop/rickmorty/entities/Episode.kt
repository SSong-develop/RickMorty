package com.ssong_develop.rickmorty.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "episode_table")
data class Episode(
    @SerialName("id")
    @PrimaryKey
    val id: Int = -1,
    @SerialName("name")
    val name: String = "",
    @SerialName("air_date")
    val airDate: String = "",
    @SerialName("episode")
    val episode: String = "",
    @SerialName("characters")
    val characters: List<String> = emptyList(),
    @SerialName("url")
    val url: String = "",
    @SerialName("created")
    val created: String = ""
)