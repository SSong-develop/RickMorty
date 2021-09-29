package com.ssong_develop.rickmorty.repository

import androidx.lifecycle.MutableLiveData
import com.ssong_develop.rickmorty.AppExecutors
import com.ssong_develop.rickmorty.entities.Location
import com.ssong_develop.rickmorty.network.ApiResponse
import com.ssong_develop.rickmorty.network.client.LocationClient
import com.ssong_develop.rickmorty.network.message
import com.ssong_develop.rickmorty.persistence.LocationDao
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val client: LocationClient,
    private val locationDao : LocationDao,
    private val appExecutors : AppExecutors
) : Repository {
    fun loadLocations(page: Int, error: (String) -> Unit): MutableLiveData<List<Location>> {
        val liveData = MutableLiveData<List<Location>>()
        var locations = locationDao.getLocations()
        client.fetchLocation(page) { response ->
            when (response) {
                is ApiResponse.Success -> {
                    response.data?.let { data ->
                        locations = data.results
                        liveData.postValue(data.results)
                        appExecutors.diskIO().execute {
                            locationDao.insertLocationList(locations)
                        }
                    }
                }
                is ApiResponse.Failure.Error -> error(response.message())
                is ApiResponse.Failure.Exception -> error(response.message())
            }
        }
        liveData.postValue(locations)
        return liveData
    }
}