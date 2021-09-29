package com.ssong_develop.rickmorty.ui.theme.episode

import androidx.lifecycle.*
import com.ssong_develop.rickmorty.entities.Episode
import com.ssong_develop.rickmorty.repository.EpisodeRepository
import com.ssong_develop.rickmorty.ui.LiveCoroutinesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(
    private val episodeRepository: EpisodeRepository
) : ViewModel() {
    private val toastLiveData: MutableLiveData<String> = MutableLiveData()

    private val episodePage: MutableStateFlow<Int> = MutableStateFlow(0)

    @ExperimentalCoroutinesApi
    val episodes: Flow<List<Episode>> = episodePage.flatMapLatest { page ->
        episodeRepository.loadEpisodes(episodePage.value) { toastLiveData.postValue(it) }
    }.catch {
        toastLiveData.postValue(it.toString())
    }.flowOn(Dispatchers.Main)

    @ExperimentalCoroutinesApi
    val loading: Flow<Boolean> = episodes.map {
        it.isEmpty()
    }

    fun morePage() {
        episodePage.value += 1
    }
}