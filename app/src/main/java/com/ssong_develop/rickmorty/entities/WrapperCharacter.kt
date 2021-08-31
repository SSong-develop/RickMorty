package com.ssong_develop.rickmorty.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WrapperCharacter(
    @SerialName("info")
    val info : CharacterInfo,
    @SerialName("results")
    val character : List<Character>
)