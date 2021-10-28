package com.ssong_develop.rickmorty.di

import com.ssong_develop.rickmorty.ui.character.CharacterActivity
import com.ssong_develop.rickmorty.ui.character.CharacterContract
import com.ssong_develop.rickmorty.ui.character.CharacterPresenter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
abstract class PresentationModule {
    @Binds
    abstract fun bindCharacterActivity(activity : CharacterActivity) : CharacterContract.View

    @Binds
    abstract fun bindCharacterPresenter(presenter : CharacterPresenter) : CharacterContract.Presenter
}