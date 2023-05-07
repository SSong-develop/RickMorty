package com.ssong_develop.core_data.model

import com.ssong_develop.core_model.Location
import com.ssong_develop.core_model.Origin
import com.ssong_develop.core_model.RickMortyCharacter

fun NetworkRickMortyCharacter.NetworkOrigin.asModel(): Origin =
    Origin(
        name = name,
        url = url
    )

fun NetworkRickMortyCharacter.NetworkLocation.asModel(): Location =
    Location(
        name = name,
        url = url
    )

fun NetworkRickMortyCharacter.asModel(): RickMortyCharacter =
    RickMortyCharacter(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = origin.asModel(),
        location = location.asModel(),
        image = image,
        episode = episode,
        url = url,
        created = created
    )