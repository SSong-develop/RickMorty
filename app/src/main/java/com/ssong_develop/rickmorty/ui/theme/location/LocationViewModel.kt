package com.ssong_develop.rickmorty.ui.theme.location

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssong_develop.rickmorty.entities.Location
import com.ssong_develop.rickmorty.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val toastLiveData: MutableLiveData<String> = MutableLiveData()

    private val locationPage: MutableStateFlow<Int> = MutableStateFlow(1)

    val loading = MutableStateFlow(true)

    @ExperimentalCoroutinesApi
    private val locationFlow: Flow<List<Location>> = locationPage.flatMapLatest { page ->
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

    fun morePage() {
        locationPage.value += 1
    }
}