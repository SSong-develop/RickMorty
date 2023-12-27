package com.ssong_develop.feature_character.model

import android.view.View

data class RickMortyCharacterTransitionAnimModel(
    val character: RickMortyCharacterUiModel,
    val viewAndTransitionNameList: Array<Pair<View, String>>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RickMortyCharacterTransitionAnimModel

        if (character != other.character) return false
        return viewAndTransitionNameList.contentEquals(other.viewAndTransitionNameList)
    }

    override fun hashCode(): Int {
        var result = character.hashCode()
        result = 31 * result + viewAndTransitionNameList.contentHashCode()
        return result
    }

    companion object {
        fun of(
            character: RickMortyCharacterUiModel,
            viewAndTransitionNameList: Array<Pair<View, String>>
        ): RickMortyCharacterTransitionAnimModel =
            RickMortyCharacterTransitionAnimModel(character, viewAndTransitionNameList)
    }
}