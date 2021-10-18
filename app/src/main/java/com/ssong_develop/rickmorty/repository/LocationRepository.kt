package com.ssong_develop.rickmorty.repository

import com.ssong_develop.rickmorty.di.IoDispatcher
import com.ssong_develop.rickmorty.entities.Location
import com.ssong_develop.rickmorty.network.client.LocationClient
import com.ssong_develop.rickmorty.persistence.LocationDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val client: LocationClient,
    private val locationDao: LocationDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : Repository {

    fun loadLocations(
        page: Int,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String) -> Unit
    ): Flow<List<Location>> = flow {
        val response = client.fetchLocation(page).results
        emit(response)
    }.catch {
        onError("Api Response Error")
        emit(emptyList())
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)
}