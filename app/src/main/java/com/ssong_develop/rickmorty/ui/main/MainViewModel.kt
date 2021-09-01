package com.ssong_develop.rickmorty.ui.main

import android.app.Application
import androidx.annotation.MainThread
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.ssong_develop.rickmorty.R
import com.ssong_develop.rickmorty.entities.Character
import com.ssong_develop.rickmorty.entities.Episode
import com.ssong_develop.rickmorty.entities.Location
import com.ssong_develop.rickmorty.entities.Theme
import com.ssong_develop.rickmorty.repository.CharacterRepository
import com.ssong_develop.rickmorty.repository.EpisodeRepository
import com.ssong_develop.rickmorty.repository.LocationRepository
import com.ssong_develop.rickmorty.ui.LiveCoroutinesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val app : Application,
    private val characterRepository: CharacterRepository,
    private val locationRepository: LocationRepository,
    private val episodeRepository: EpisodeRepository
) : LiveCoroutinesViewModel() {

    var testValue = 1

    val toastLiveData: MutableLiveData<String> = MutableLiveData()

    val dummy = listOf(
        Theme(ContextCompat.getDrawable(app,R.drawable.ic_launcher_foreground)!!,"test1"),
        Theme(ContextCompat.getDrawable(app,R.drawable.ic_launcher_foreground)!!,"test2"),
        Theme(ContextCompat.getDrawable(app,R.drawable.ic_launcher_foreground)!!,"test3")
    )

    private val characterPageLiveData: MutableLiveData<Int> = MutableLiveData()
    val characters: LiveData<List<Character>> = characterPageLiveData.switchMap { page ->
        launchOnViewModelScope {
            characterRepository.loadCharacters(page) { toastLiveData.postValue(it) }
        }
    }

    private val locationPageLiveData: MutableLiveData<Int> = MutableLiveData()
    val locations: LiveData<List<Location>> = locationPageLiveData.switchMap { page ->
        launchOnViewModelScope {
            locationRepository.loadLocations(page) { toastLiveData.postValue(it) }
        }
    }

    private val episodePageLiveData: MutableLiveData<Int> = MutableLiveData()
    val episodes: LiveData<List<Episode>> = episodePageLiveData.switchMap { page ->
        launchOnViewModelScope {
            episodeRepository.loadEpisodes(page) { toastLiveData.postValue(it) }
        }
    }

    fun isLoading() =
        characterRepository.isLoading || locationRepository.isLoading || episodeRepository.isLoading

    @MainThread
    fun refresh() {
        locationPageLiveData.value = testValue
        testValue++
    }
}