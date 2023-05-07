package com.ssong_develop.core_model

data class RickMortyCharacterEpisode(
    val id: Int,
    val name: String,
    val airDate: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)
