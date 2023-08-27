package com.ssong_develop.core_database.di

import android.content.Context
import androidx.room.Room
import com.ssong_develop.core_database.database.RickMortyCharacterDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        RickMortyCharacterDatabase::class.java, "rick_and_morty_database"
    )
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideCharactersDao(database: RickMortyCharacterDatabase) = database.characterDao()

    @Provides
    @Singleton
    fun provideRecentSearchKeywordDao(database: RickMortyCharacterDatabase) =
        database.recentSearchKeywordDao()
}