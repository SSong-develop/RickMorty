package com.ssong_develop.rickmorty.di

import com.ssong_develop.rickmorty.ui.delegate.FavoriteCharacterDelegate
import com.ssong_develop.rickmorty.ui.delegate.FavoriteCharacterDelegateImpl
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
    internal abstract fun bindFavoriteCharacterDelegate(favoriteCharacterDelegateImpl: FavoriteCharacterDelegateImpl): FavoriteCharacterDelegate
}