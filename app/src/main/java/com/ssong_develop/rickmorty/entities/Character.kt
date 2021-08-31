package com.ssong_develop.rickmorty.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Character(
    @SerialName("id")
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
    val origin: Origin,
    @SerialName("location")
    val location: Location,
    @SerialName("image")
    val image: String,
    @SerialName("episode")
    val episode: List<String>,
    @SerialName("url")
    val url: String,
    @SerialName("created")
    val created: String
) : Parcelable {

    @Parcelize
    @Serializable
    data class Origin(
        @SerialName("name")
        val name: String,
        @SerialName("url")
        val url: String
    ) : Parcelable

    @Parcelize
    @Serializable
    data class Location(
        @SerialName("name")
        val name: String,
        @SerialName("url")
        val url: String
    ) : Parcelable
}