package com.ssong_develop.feature_character.model

import com.ssong_develop.core_model.RickMortyCharacter

fun RickMortyCharacterUiModel.OriginUiModel.asModel() =
    RickMortyCharacter.Origin(name, url)

fun RickMortyCharacterUiModel.LocationUiModel.asModel() =
    RickMortyCharacter.Location(name, url)

fun RickMortyCharacterUiModel.asModel() = RickMortyCharacter(
    id,
    name,
    status,
    species,
    type,
    gender,
    origin.asModel(),
    location.asModel(),
    image,
    episode,
    url,
    created
)