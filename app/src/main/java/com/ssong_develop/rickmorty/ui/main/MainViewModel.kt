package com.ssong_develop.rickmorty.ui.main

import android.app.Application
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.ssong_develop.rickmorty.R
import com.ssong_develop.rickmorty.entities.Theme
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val app: Application,
) : ViewModel() {

    private val defaultThemeList = listOf(
        Theme(ContextCompat.getDrawable(app, R.drawable.rick)!!, app.getString(R.string.character)),
        Theme(
            ContextCompat.getDrawable(app, R.drawable.location)!!,
            app.getString(R.string.location)
        ),
        Theme(ContextCompat.getDrawable(app, R.drawable.episode)!!, app.getString(R.string.episode))
    )

    fun getDefaultThemeList() = defaultThemeList
}