package com.ssong_develop.feature_favorite.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.ssong_develop.core_common.WhileViewSubscribed
import com.ssong_develop.core_common.extension.convertStringToDate
import com.ssong_develop.core_common.extension.parseToYYYYmmDD
import com.ssong_develop.core_data.model.asModel
import com.ssong_develop.core_data.repository.CharacterRepository
import com.ssong_develop.core_datastore.PreferenceStorage
import com.ssong_develop.core_model.RickMortyCharacter
import com.ssong_develop.core_model.RickMortyCharacterEpisode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

internal sealed interface UiState {
    data class HasFavoriteCharacter(
        val isLoading: Boolean = true,
        val favoriteCharacter: RickMortyCharacter,
        val episode: List<RickMortyCharacterEpisode>
    ) : UiState {
        val episodeAirDates
            get() = episode.mapNotNull {
                it.airDate.parseToYYYYmmDD().convertStringToDate()
            }
    }

    data object NoFavoriteCharacter : UiState
    data object Loading : UiState
}

@OptIn(ExperimentalPagingApi::class)
@ExperimentalCoroutinesApi
@HiltViewModel
internal class FavoriteViewModel @Inject constructor(
    preferenceStorage: PreferenceStorage,
    private val repository: CharacterRepository
) : ViewModel() {

    private val hasFavoriteCharacterState: Flow<Pair<Boolean, RickMortyCharacter?>> =
        preferenceStorage.favoriteCharacter.map { favCharacter ->
            if (favCharacter != null) {
                true to favCharacter
            } else {
                false to null
            }
        }

    val uiState = hasFavoriteCharacterState.mapLatest {
        if (it.first) {
            val favCharacter = it.second!!
            val episodes = getFavCharacterEpisodes(it.second!!.episode)
            UiState.HasFavoriteCharacter(
                favoriteCharacter = favCharacter,
                episode = episodes
            )
        } else {
            UiState.NoFavoriteCharacter
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileViewSubscribed,
        initialValue = UiState.Loading
    )

    private suspend fun getFavCharacterEpisodes(episodeUrls: List<String>) =
        runCatching {
            repository.getEpisodes(episodeUrls)
        }.onSuccess {

        }.mapCatching { episodes ->
            episodes?.map { episode -> episode.asModel() } ?: emptyList()
        }.getOrDefault(emptyList())
}