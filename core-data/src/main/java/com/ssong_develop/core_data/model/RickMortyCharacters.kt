package com.ssong_develop.core_data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkResult<T,V>(
    val info: T,
    val results: List<V>
)

@Serializable
data class NetworkRickMortyCharacterInfo(
    @SerialName("count")
    val count: Int,
    @SerialName("pages")
    val pages: Int,
    @SerialName("next")
    val next: String,
    @SerialName("prev")
    val prev: String
)

@Serializable
data class NetworkRickMortyCharacter(
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
) {
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
}

@Serializable
data class NetworkRickMortyCharacterEpisode(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("airDate")
    val airDate: String,
    @SerialName("episode")
    val episode: String,
    @SerialName("characters")
    val characters: List<String>,
    @SerialName("url")
    val url: String,
    @SerialName("created")
    val created: String
)

