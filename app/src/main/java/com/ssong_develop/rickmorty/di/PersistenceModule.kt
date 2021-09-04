package com.ssong_develop.rickmorty.di

import android.content.Context
import androidx.room.Room
import com.ssong_develop.rickmorty.persistence.AppDatabase
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
        AppDatabase::class.java, "rick_and_morty_database"
    )
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideCharactersDao(database : AppDatabase) = database.characterDao()

    @Provides
    @Singleton
    fun provideEpisodeDao(database : AppDatabase) = database.episodeDao()

    @Provides
    fun provideLocationDao(database : AppDatabase) = database.locationDao()
}