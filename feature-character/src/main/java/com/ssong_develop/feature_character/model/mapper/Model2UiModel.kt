package com.ssong_develop.feature_character.model.mapper

import com.ssong_develop.core_model.RickMortyCharacter
import com.ssong_develop.feature_character.model.RickMortyCharacterUiModel

fun RickMortyCharacter.Location.asUiModel() = RickMortyCharacterUiModel.LocationUiModel(
    name,
    url
)

fun RickMortyCharacter.Origin.asUiModel(): RickMortyCharacterUiModel.OriginUiModel =
    RickMortyCharacterUiModel.OriginUiModel(
        name = name,
        url = url
    )

fun RickMortyCharacter.asUiModel(): RickMortyCharacterUiModel =
    RickMortyCharacterUiModel(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = origin.asUiModel(),
        location = location.asUiModel(),
        image = image,
        episode = episode,
        url = url,
        created = created,
        dominantColor = dominantColor
    )