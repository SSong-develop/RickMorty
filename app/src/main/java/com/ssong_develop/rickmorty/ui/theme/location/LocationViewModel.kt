package com.ssong_develop.rickmorty.ui.theme.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.switchMap
import com.ssong_develop.rickmorty.entities.Location
import com.ssong_develop.rickmorty.repository.LocationRepository
import com.ssong_develop.rickmorty.ui.LiveCoroutinesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : LiveCoroutinesViewModel() {

    private val toastLiveData: MutableLiveData<String> = MutableLiveData()

    private val locationPage: MutableStateFlow<Int> = MutableStateFlow(0)
    val locations: Flow<List<Location>> = locationPage.flatMapLatest { page ->
        locationRepository.loadLocations(locationPage.value){toastLiveData.postValue(it)}
    }.catch {
        toastLiveData.postValue(it.toString())
    }.flowOn(Dispatchers.Main)

    val loading : Flow<Boolean> = locations.map {
        it.isEmpty()
    }

    fun morePage(){
        locationPage.value += 1
    }
}