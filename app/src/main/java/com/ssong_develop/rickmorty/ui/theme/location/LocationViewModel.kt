package com.ssong_develop.rickmorty.ui.theme.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.ssong_develop.rickmorty.entities.Location
import com.ssong_develop.rickmorty.repository.LocationRepository
import com.ssong_develop.rickmorty.ui.LiveCoroutinesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : LiveCoroutinesViewModel() {

    val toastLiveData: MutableLiveData<String> = MutableLiveData()

    private val locationPageLiveData: MutableLiveData<Int> = MutableLiveData()
    val locations: LiveData<List<Location>> = locationPageLiveData.switchMap { page ->
        launchOnViewModelScope {
            locationRepository.loadLocations(page) { toastLiveData.postValue(it) }
        }
    }

    fun isLoading() = locationRepository.isLoading

    fun initialFetchLocations(value: Int) {
        locationPageLiveData.value = value
    }

    fun morePage() = locationPageLiveData.value?.plus(1)
}