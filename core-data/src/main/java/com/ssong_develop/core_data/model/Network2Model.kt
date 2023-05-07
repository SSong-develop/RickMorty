package com.ssong_develop.core_data.model

import com.ssong_develop.core_model.RickMortyCharacter
import com.ssong_develop.core_model.RickMortyCharacterEpisode

fun NetworkRickMortyCharacter.NetworkOrigin.asModel(): RickMortyCharacter.Origin =
    RickMortyCharacter.Origin(
        name = name,
        url = url
    )

fun NetworkRickMortyCharacter.NetworkLocation.asModel(): RickMortyCharacter.Location =
    RickMortyCharacter.Location(
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

fun NetworkRickMortyCharacterEpisode.asModel(): RickMortyCharacterEpisode =
    RickMortyCharacterEpisode(
        id = id,
        name = name,
        airDate = airDate,
        episode = episode,
        characters = characters,
        url = url,
        created = created
    )