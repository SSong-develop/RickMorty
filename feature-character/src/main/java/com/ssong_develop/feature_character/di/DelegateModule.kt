package com.ssong_develop.feature_character.di

import com.ssong_develop.feature_character.delegate.FavoriteCharacterDelegate
import com.ssong_develop.feature_character.delegate.FavoriteCharacterDelegateImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class DelegateModule {

    @Binds
    @Singleton
    internal abstract fun bindFavoriteCharacterDelegate(favoriteCharacterDelegateImpl: com.ssong_develop.feature_character.delegate.FavoriteCharacterDelegateImpl): com.ssong_develop.feature_character.delegate.FavoriteCharacterDelegate
}