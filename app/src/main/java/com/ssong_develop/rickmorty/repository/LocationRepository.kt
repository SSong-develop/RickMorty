package com.ssong_develop.rickmorty.repository

import androidx.lifecycle.MutableLiveData
import com.ssong_develop.rickmorty.entities.Location
import com.ssong_develop.rickmorty.network.ApiResponse
import com.ssong_develop.rickmorty.network.client.LocationClient
import com.ssong_develop.rickmorty.network.message
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val client: LocationClient
) : Repository {
    override var isLoading = false

    fun loadLocations(page: Int, error: (String) -> Unit): MutableLiveData<List<Location>> {
        val liveData = MutableLiveData<List<Location>>()
        isLoading = true
        client.fetchLocation(page) { response ->
            isLoading = false
            when (response) {
                is ApiResponse.Success -> {
                    response.data?.let { data ->
                        liveData.postValue(data.results)
                    }
                }
                is ApiResponse.Failure.Error -> error(response.message())
                is ApiResponse.Failure.Exception -> error(response.message())
            }
        }
        return liveData
    }
}