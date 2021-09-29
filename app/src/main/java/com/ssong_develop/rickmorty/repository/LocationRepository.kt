package com.ssong_develop.rickmorty.repository

import androidx.lifecycle.MutableLiveData
import com.ssong_develop.rickmorty.AppExecutors
import com.ssong_develop.rickmorty.entities.Episode
import com.ssong_develop.rickmorty.entities.Location
import com.ssong_develop.rickmorty.network.ApiResponse
import com.ssong_develop.rickmorty.network.client.LocationClient
import com.ssong_develop.rickmorty.network.message
import com.ssong_develop.rickmorty.persistence.LocationDao
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val client: LocationClient,
    private val locationDao : LocationDao,
    private val appExecutors : AppExecutors
) : Repository {

    fun loadLocations(page: Int, error: (String) -> Unit): MutableStateFlow<List<Location>> {
        var locations = emptyList<Location>()
        appExecutors.diskIO().execute {
            locations = locationDao.getLocations()
        }
        val stateFlow = MutableStateFlow<List<Location>>(locations)
        client.fetchLocation(page) { response ->
            when (response) {
                is ApiResponse.Success -> {
                    response.data?.let { data ->
                        locations = data.results
                        appExecutors.mainThread().execute {
                            stateFlow.value = data.results
                        }
                        appExecutors.diskIO().execute {
                            locationDao.insertLocationList(locations)
                        }
                    }
                }
                is ApiResponse.Failure.Error -> error(response.message())
                is ApiResponse.Failure.Exception -> error(response.message())
            }
        }
        stateFlow.value = locations
        return stateFlow
    }
}