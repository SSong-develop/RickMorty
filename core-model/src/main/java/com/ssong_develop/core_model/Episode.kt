package com.ssong_develop.core_model

import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Episode(
    /** The id of the episode */
    @SerialName("id")
    @PrimaryKey
    val id: Int = -1,
    /** The name of the episode */
    @SerialName("name")
    val name: String = "",
    /** The air date of the episode */
    @SerialName("air_date")
    val airDate: String = "",
    /** The code of the episode */
    @SerialName("episode")
    val episode: String = "",
    /** List of characters who have been seen in the episode */
    @SerialName("characters")
    val characters: List<String> = emptyList(),
    /** Link to the episode's own endpoint */
    @SerialName("url")
    val url: String = "",
    /** Time at which the episode was created in the database */
    @SerialName("created")
    val created: String = ""
)