package com.ssong_develop.core_datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ssong_develop.core_model.ChatMemo
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

    val chatMemoFlow: Flow<List<ChatMemo>>

    suspend fun addFavoriteCharacter(characters: RickMortyCharacter)

    suspend fun removeFavoriteCharacter()

    suspend fun addChatMemo(chatMemo: ChatMemo)

    suspend fun removeChatMemo(messageId: String)

    suspend fun updateChatMemo(chatMemo: ChatMemo)
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

    override val chatMemoFlow: Flow<List<ChatMemo>> =
        context.datastore.data.map { preferences ->
            preferences[PREFERENCES_CHAT_MEMO]?.let { chatMemoJson ->
                if (chatMemoJson.isEmpty()) {
                    emptyList()
                } else {
                    json.decodeFromString<List<ChatMemo>>(chatMemoJson)
                }
            } ?: emptyList()
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

    override suspend fun addChatMemo(chatMemo: ChatMemo) {
        context.datastore.edit { mutablePreferences ->
            val chatMemoList = mutablePreferences[PREFERENCES_CHAT_MEMO]?.let {
                json.decodeFromString<List<ChatMemo>>(it).toMutableList()
            } ?: run {
                mutableListOf()
            }

            chatMemoList.add(chatMemo)

            mutablePreferences[PREFERENCES_CHAT_MEMO] =
                json.encodeToString(chatMemoList.toList())
        }
    }

    override suspend fun removeChatMemo(messageId: String) {
        context.datastore.edit { mutablePreferences ->
            val chatMemoList = json.decodeFromString<List<ChatMemo>>(
                requireNotNull(mutablePreferences[PREFERENCES_CHAT_MEMO])
            ).toMutableList()

            mutablePreferences[PREFERENCES_CHAT_MEMO] =
                json.encodeToString(
                    chatMemoList.removeIf { chatMemo ->
                        chatMemo.id == messageId
                    }
                )
        }
    }

    override suspend fun updateChatMemo(chatMemo: ChatMemo) {
        context.datastore.edit { mutablePreferences ->
            val chatMemoList = json.decodeFromString<List<ChatMemo>>(
                requireNotNull(mutablePreferences[PREFERENCES_CHAT_MEMO])
            ).toMutableList()

            val index = chatMemoList.withIndex().find { it.value.id == chatMemo.id }?.index ?: -1

            if (index != -1) {
                chatMemoList[index] = chatMemo
            }

            mutablePreferences[PREFERENCES_CHAT_MEMO] =
                json.encodeToString(chatMemoList)
        }
    }

    companion object {
        private val PREFERENCES_FAVORITE_CHARACTER = stringPreferencesKey("favorite_character")
        private val PREFERENCES_CHAT_MEMO = stringPreferencesKey("chat_memo")
    }
}
