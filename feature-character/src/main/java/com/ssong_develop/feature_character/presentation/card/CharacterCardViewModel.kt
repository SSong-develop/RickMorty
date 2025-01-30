package com.ssong_develop.feature_character.presentation.card

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterCardViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val imageUrl = savedStateHandle.get<String>("image")

}