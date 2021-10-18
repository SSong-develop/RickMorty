package com.ssong_develop.rickmorty.ui.theme.location

import androidx.lifecycle.*
import com.ssong_develop.rickmorty.entities.Location
import com.ssong_develop.rickmorty.repository.LocationRepository
import com.ssong_develop.rickmorty.ui.LiveCoroutinesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : LiveCoroutinesViewModel() {

    private val toastLiveData: MutableLiveData<String> = MutableLiveData()

    private val locationPage: MutableStateFlow<Int> = MutableStateFlow(0)

    val loading = MutableStateFlow(true)

    @ExperimentalCoroutinesApi
    private val locationFlow : Flow<List<Location>> = locationPage.flatMapLatest { page ->
        locationRepository.loadLocations(
            page = page,
            onStart = { loading.value = true },
            onComplete = { loading.value = false },
            onError = {
                loading.value = true
                toastLiveData.postValue(it)
            }
        )
    }

    @ExperimentalCoroutinesApi
    val locationStateFlow = locationFlow.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun morePage(){
        locationPage.value += 1
    }
}