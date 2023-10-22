package com.ssong_develop.feature_character.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RickMortyCharacterUiModel(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: OriginUiModel,
    val location: LocationUiModel,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String,
    var dominantColor: Int? = null
) : Parcelable {

    @Parcelize
    data class OriginUiModel(
        val name: String,
        val url: String
    ) : Parcelable

    @Parcelize
    data class LocationUiModel(
        val name: String,
        val url: String
    ) : Parcelable
}

