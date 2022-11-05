package com.ssong_develop.core_datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.ssong_develop.core_model.Characters
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface DataStoreRepository {
    val favoriteCharacterFlow: Flow<Characters?>

    suspend fun putFavoriteCharacter(characters: Characters)

    suspend fun clearFavoriteCharacter()
}

internal class DataStoreRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : DataStoreRepository {

    companion object {
        private val PREFERENCES_FAVORITE_CHARACTER = stringPreferencesKey("favorite_character")
    }

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "rick_morty_data_store")

    override val favoriteCharacterFlow: Flow<Characters?> =
        context.datastore.data.map { preferences ->
            preferences[PREFERENCES_FAVORITE_CHARACTER]?.let { characterJson ->
                // 여기 구문 고치기
                if (characterJson.isNotEmpty()) {
                    Gson().fromJson(characterJson, Characters::class.java)
                } else {
                    null
                }
            }
        }

    override suspend fun putFavoriteCharacter(characters: Characters) {
        context.datastore.edit { mutablePreferences ->
            mutablePreferences[PREFERENCES_FAVORITE_CHARACTER] = Gson().toJson(characters)
        }
    }

    override suspend fun clearFavoriteCharacter() {
        context.datastore.edit { mutablePreferences ->
            mutablePreferences[PREFERENCES_FAVORITE_CHARACTER] = ""
        }
    }
}
