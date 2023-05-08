package com.ssong_develop.core_data.model

import com.ssong_develop.core_database.model.LocalEntityLocation
import com.ssong_develop.core_database.model.LocalEntityOrigin
import com.ssong_develop.core_database.model.LocalEntityRickMortyCharacter
import com.ssong_develop.core_model.RickMortyCharacter

fun LocalEntityOrigin.asModel(): RickMortyCharacter.Origin =
    RickMortyCharacter.Origin(name, url)

fun LocalEntityLocation.asModel(): RickMortyCharacter.Location =
    RickMortyCharacter.Location(name, url)

fun LocalEntityRickMortyCharacter.asModel(): RickMortyCharacter =
    RickMortyCharacter(
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