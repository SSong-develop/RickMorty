package com.ssong_develop.feature_character.character.viewholders

import android.view.View
import com.ssong_develop.core_model.Characters

interface ItemClickDelegate {
    fun onItemClick(
        characterImageView: View,
        characterNameView: View,
        characterImageTransitionName: String,
        characterNameTransitionName: String,
        characters: Characters
    )
}