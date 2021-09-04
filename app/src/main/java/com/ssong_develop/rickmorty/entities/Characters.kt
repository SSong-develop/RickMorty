package com.ssong_develop.rickmorty.entities

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "characters_table")
data class Characters(
    @SerialName("id")
    @PrimaryKey
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("status")
    val status: String,
    @SerialName("species")
    val species: String,
    @SerialName("type")
    val type: String,
    @SerialName("gender")
    val gender: String,
    @SerialName("origin")
    @Embedded
    var origin: Origin? = null,
    @SerialName("location")
    @Embedded
    var location: Location? = null,
    @SerialName("image")
    val image: String,
    @SerialName("episode")
    val episode: List<String>,
    @SerialName("url")
    val url: String,
    @SerialName("created")
    val created: String
) {

    @Serializable
    data class Origin(
        @SerialName("name")
        val originName: String,
        @SerialName("url")
        val originUrl: String
    )

    @Serializable
    data class Location(
        @SerialName("name")
        val locationName: String,
        @SerialName("url")
        val locationUrl: String
    )
}