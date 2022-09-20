package com.ssong_develop.rickmorty.persistence.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface RickMortyDataStore {

}

class RickMortyDataStoreImpl @Inject constructor(
    @ApplicationContext private val context : Context
) : RickMortyDataStore {
    private val Context.datastore : DataStore<Preferences> by preferencesDataStore(name = "rick_morty_data_store")

}
