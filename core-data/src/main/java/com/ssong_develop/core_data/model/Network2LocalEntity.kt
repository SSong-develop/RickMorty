package com.ssong_develop.core_data.model

import com.ssong_develop.core_database.model.LocalEntityLocation
import com.ssong_develop.core_database.model.LocalEntityOrigin
import com.ssong_develop.core_database.model.LocalEntityRickMortyCharacter
import com.ssong_develop.core_database.model.LocalEntityRickMortyCharacterInfo

fun NetworkRickMortyCharacter.NetworkOrigin.asLocalEntity(): LocalEntityOrigin =
    LocalEntityOrigin(name, url)

fun NetworkRickMortyCharacter.NetworkLocation.asLocalEntity(): LocalEntityLocation =
    LocalEntityLocation(name, url)

fun NetworkRickMortyCharacterInfo.asLocalEntity(): LocalEntityRickMortyCharacterInfo =
    LocalEntityRickMortyCharacterInfo(count, pages, next, prev)

fun NetworkRickMortyCharacter.asLocalEntity(
    networkRickMortyCharacterInfo: NetworkRickMortyCharacterInfo
): LocalEntityRickMortyCharacter =
    LocalEntityRickMortyCharacter(
        id,
        networkRickMortyCharacterInfo.asLocalEntity(),
        name,
        status,
        species,
        type,
        gender,
        origin.asLocalEntity(),
        location.asLocalEntity(),
        image,
        episode,
        url,
        created
    )