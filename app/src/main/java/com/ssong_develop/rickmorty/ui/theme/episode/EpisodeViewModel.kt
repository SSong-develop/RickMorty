package com.ssong_develop.rickmorty.ui.theme.episode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.switchMap
import com.ssong_develop.rickmorty.entities.Episode
import com.ssong_develop.rickmorty.repository.EpisodeRepository
import com.ssong_develop.rickmorty.ui.LiveCoroutinesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(
    private val episodeRepository: EpisodeRepository
) : LiveCoroutinesViewModel() {

    private val toastLiveData: MutableLiveData<String> = MutableLiveData()

    private val episodePageLiveData: MutableLiveData<Int> = MutableLiveData()
    val episodes: LiveData<List<Episode>> = episodePageLiveData.switchMap { page ->
        launchOnViewModelScope {
            episodeRepository.loadEpisodes(page) { toastLiveData.postValue(it) }
        }
    }

    val loading = MutableLiveData<Boolean>(episodeRepository.isLoading)

    fun initialFetchEpisodes(value: Int) {
        episodePageLiveData.value = value
    }

    fun morePage(){
        episodePageLiveData.value = episodePageLiveData.value!!.plus(1)
    }
}