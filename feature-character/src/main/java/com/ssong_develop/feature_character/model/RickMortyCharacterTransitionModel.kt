package com.ssong_develop.feature_character.model

import android.view.View

data class RickMortyCharacterTransitionModel(
    val character: RickMortyCharacterUiModel,
    val viewAndTransitionNameList: Array<Pair<View, String>>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RickMortyCharacterTransitionModel

        if (character != other.character) return false
        if (!viewAndTransitionNameList.contentEquals(other.viewAndTransitionNameList)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = character.hashCode()
        result = 31 * result + viewAndTransitionNameList.contentHashCode()
        return result
    }
}