package com.ssong_develop.rickmorty.persistence.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface RickMortyDataStore {

    val favoriteCharacterIdFlow : Flow<Int?>

    suspend fun putFavoriteCharacterId(id: Int)

    suspend fun clearFavoriteCharacterId()
}

class RickMortyDataStoreImpl @Inject constructor(
    @ApplicationContext private val context : Context
) : RickMortyDataStore {

    companion object {
        private val PREFERENCES_FAVORITE_CHARACTER_ID = intPreferencesKey("favorite_character_id")
    }

    private val Context.datastore : DataStore<Preferences> by preferencesDataStore(name = "rick_morty_data_store")

    override val favoriteCharacterIdFlow: Flow<Int?> = context.datastore.data.map { preferences ->
        preferences[PREFERENCES_FAVORITE_CHARACTER_ID]
    }

    override suspend fun putFavoriteCharacterId(id: Int) {
        context.datastore.edit { mutablePreferences ->
            mutablePreferences[PREFERENCES_FAVORITE_CHARACTER_ID] = id
        }
    }

    override suspend fun clearFavoriteCharacterId() {
        context.datastore.edit { mutablePreferences ->
            mutablePreferences[PREFERENCES_FAVORITE_CHARACTER_ID] = -2
        }
    }
}
