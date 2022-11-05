package com.ssong_develop.feature_character.favorite

import androidx.lifecycle.ViewModel
import com.ssong_develop.feature_character.delegate.FavoriteCharacterDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteCharacterViewModel @Inject constructor(
    private val favoriteCharacterDelegate: FavoriteCharacterDelegate
) : ViewModel(), FavoriteCharacterDelegate by favoriteCharacterDelegate {

}