package com.ssong_develop.feature_character.model

import java.util.UUID

data class RickMortyAdvertiseUiModel(
    val advertiseId: String = UUID.randomUUID().toString(),
    val imageUrl: String,
    val advertiseText: String
)
