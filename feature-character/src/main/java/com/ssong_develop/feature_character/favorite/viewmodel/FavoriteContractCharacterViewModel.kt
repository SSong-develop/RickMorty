package com.ssong_develop.feature_character.favorite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssong_develop.core_common.Resource
import com.ssong_develop.feature_character.delegate.FavoriteCharacterDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteContractCharacterViewModel @Inject constructor(
    private val favoriteCharacterDelegate: com.ssong_develop.feature_character.delegate.FavoriteCharacterDelegate
) : ViewModel(),
    com.ssong_develop.feature_character.delegate.FavoriteCharacterDelegate by favoriteCharacterDelegate {

    val favoriteCharacter = favCharacterFlow.map { resource ->
        when (resource.status) {
            Resource.Status.SUCCESS -> {
                resource.data
            }
            else -> {
                null
            }
        }
    }
}