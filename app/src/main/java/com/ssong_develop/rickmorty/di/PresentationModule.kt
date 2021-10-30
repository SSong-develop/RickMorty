package com.ssong_develop.rickmorty.di

import com.ssong_develop.rickmorty.ui.character.CharacterActivity
import com.ssong_develop.rickmorty.ui.character.CharacterContract
import com.ssong_develop.rickmorty.ui.character.CharacterPresenter
import com.ssong_develop.rickmorty.ui.detail.CharacterDetailActivity
import com.ssong_develop.rickmorty.ui.detail.CharacterDetailContract
import com.ssong_develop.rickmorty.ui.detail.CharacterDetailPresenter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
abstract class PresentationModule {
    @Binds
    abstract fun bindCharacterActivity(activity : CharacterActivity) : CharacterContract.CharactersView

    @Binds
    abstract fun bindCharacterPresenter(presenter : CharacterPresenter) : CharacterContract.CharactersPresenter

    @Binds
    abstract fun bindCharacterDetailActivity(activity : CharacterDetailActivity) : CharacterDetailContract.CharacterDetailView

    @Binds
    abstract fun bindCharacterDetailPresenter(presenter : CharacterDetailPresenter) : CharacterDetailContract.CharacterDetailPresenter
}