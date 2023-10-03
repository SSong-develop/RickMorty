package com.ssong_develop.core_datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.ssong_develop.core_model.RickMortyCharacter
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

interface PreferenceStorage {
    val favoriteCharacter: Flow<RickMortyCharacter?>

    suspend fun addFavoriteCharacter(characters: RickMortyCharacter)

    suspend fun removeFavoriteCharacter()
}

internal class DataStorePreferenceStorage @Inject constructor(
    @ApplicationContext private val context: Context
) : PreferenceStorage {

    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "rick_morty_data_store")

    override val favoriteCharacter: Flow<RickMortyCharacter?> =
        context.datastore.data.map { preferences ->
            preferences[PREFERENCES_FAVORITE_CHARACTER]?.let { characterJson ->
                if (characterJson.isNotEmpty()) {
                    json.decodeFromString<RickMortyCharacter>(characterJson)
                } else {
                    null
                }
            }
        }

    override suspend fun addFavoriteCharacter(characters: RickMortyCharacter) {
        context.datastore.edit { mutablePreferences ->
            mutablePreferences[PREFERENCES_FAVORITE_CHARACTER] = json.encodeToString(characters)
        }
    }

    override suspend fun removeFavoriteCharacter() {
        context.datastore.edit { mutablePreferences ->
            mutablePreferences[PREFERENCES_FAVORITE_CHARACTER] = ""
        }
    }

    companion object {
        private val PREFERENCES_FAVORITE_CHARACTER = stringPreferencesKey("favorite_character")
    }
}
