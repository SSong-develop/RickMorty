package com.ssong_develop.core_sharedpref

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.ssong_develop.core_model.RickMortyCharacter
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

interface PreferenceStorage {
    val favoriteCharacter: Flow<RickMortyCharacter?>

    suspend fun addFavoriteCharacter(characters: RickMortyCharacter)

    suspend fun removeFavoriteCharacter()
}

class SharedPreferenceStorage @Inject constructor(
    @ApplicationContext private val context: Context
) : PreferenceStorage {

    companion object {
        private const val FAVORITE_KEY = "rick_morty_favorite"
    }

    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    private val sharedPreference = context.getSharedPreferences("rick_morty_shared_preference", 0)

    override val favoriteCharacter: Flow<RickMortyCharacter?> = callbackFlow {
        val callbackImpl = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == FAVORITE_KEY) {
                trySend(getFavoriteCharacter())
            }
        }

        sharedPreference.registerOnSharedPreferenceChangeListener(callbackImpl)

        if (sharedPreference.contains(FAVORITE_KEY)) {
            send(getFavoriteCharacter())
        }

        awaitClose {
            sharedPreference.unregisterOnSharedPreferenceChangeListener(callbackImpl)
        }
    }

    override suspend fun addFavoriteCharacter(characters: RickMortyCharacter) {
        sharedPreference.edit(true) {
            val jsonString = json.encodeToString(characters)
            putString(FAVORITE_KEY, jsonString)
        }
    }

    override suspend fun removeFavoriteCharacter() {
        sharedPreference.edit(true) {
            putString(FAVORITE_KEY, null)
        }
    }

    private fun getFavoriteCharacter(): RickMortyCharacter? =
        sharedPreference.getString(FAVORITE_KEY, null)?.let {
            json.decodeFromString<RickMortyCharacter>(it)
        }
}