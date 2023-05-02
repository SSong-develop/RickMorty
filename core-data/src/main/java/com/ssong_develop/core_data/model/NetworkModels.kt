package com.ssong_develop.core_data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCharacters(
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
    val origin: NetworkOrigin,
    @SerialName("location")
    val location: NetworkLocation,
    @SerialName("image")
    val image: String,
    @SerialName("episode")
    val episode: List<String>,
    @SerialName("url")
    val url: String,
    @SerialName("created")
    val created: String
)

@Serializable
data class NetworkOrigin(
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String
)

@Serializable
data class NetworkLocation(
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String
)