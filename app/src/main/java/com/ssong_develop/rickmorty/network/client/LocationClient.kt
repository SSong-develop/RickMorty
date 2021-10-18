package com.ssong_develop.rickmorty.network.client

import com.ssong_develop.rickmorty.network.service.LocationService
import javax.inject.Inject

class LocationClient @Inject constructor(
    private val service: LocationService
) {
    suspend fun fetchLocation(page: Int) = service.fetchLocations(page)
}