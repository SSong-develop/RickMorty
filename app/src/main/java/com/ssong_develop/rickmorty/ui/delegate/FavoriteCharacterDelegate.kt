package com.ssong_develop.rickmorty.ui.delegate

import com.ssong_develop.rickmorty.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

interface FavoriteCharacterDelegate {

}

class FavoriteCharacterDelegateImpl @Inject constructor(
    @ApplicationScope private val applicationScope: CoroutineScope
) : FavoriteCharacterDelegate
