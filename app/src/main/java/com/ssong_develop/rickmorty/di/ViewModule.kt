package com.ssong_develop.rickmorty.di

import android.app.Activity
import com.ssong_develop.rickmorty.ui.character.CharacterActivity
import com.ssong_develop.rickmorty.ui.detail.CharacterDetailActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object ViewModule {
    @Provides
    fun provideCharacterActivity(activity : Activity) : CharacterActivity = activity as CharacterActivity

    @Provides
    fun provideCharacterDetailActivity(activity : Activity) : CharacterDetailActivity = activity as CharacterDetailActivity
}