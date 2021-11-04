package utils

import com.ssong_develop.rickmorty.entities.Characters

object MockUtil {

    fun mockCharacter() = Characters(
        page = 0,
        name = "Rick",
        url = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
    )

    fun mockCharacterList() = listOf(mockCharacter())
}