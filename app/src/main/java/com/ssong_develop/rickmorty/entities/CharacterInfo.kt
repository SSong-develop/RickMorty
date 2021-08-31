package com.ssong_develop.rickmorty.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterInfo(
    @SerialName("count")
    val count: Int,
    @SerialName("pages")
    val pages: Int,
    @SerialName("next")
    val next: String,
    @SerialName("prev")
    val prev: String?
)
