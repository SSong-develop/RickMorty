package com.ssong_develop.feature_favorite

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {


}