package com.ssong_develop.rickmorty.network.client

import com.ssong_develop.rickmorty.entities.Location
import com.ssong_develop.rickmorty.entities.base.Info
import com.ssong_develop.rickmorty.entities.base.Wrapper
import com.ssong_develop.rickmorty.network.ApiResponse
import com.ssong_develop.rickmorty.network.service.LocationService
import com.ssong_develop.rickmorty.network.transform
import javax.inject.Inject

class LocationClient @Inject constructor(
    private val service: LocationService
) {
    fun fetchLocation(
        page: Int,
        onResult: (response: ApiResponse<Wrapper<Info, Location>>) -> Unit
    ) {
        service.fetchLocations(page).transform(onResult)
    }
}