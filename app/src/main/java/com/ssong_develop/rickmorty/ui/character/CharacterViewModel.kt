package com.ssong_develop.rickmorty.ui.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ssong_develop.rickmorty.di.IoDispatcher
import com.ssong_develop.rickmorty.entities.Characters
import com.ssong_develop.rickmorty.repository.CharacterRepository
import com.ssong_develop.rickmorty.ui.delegate.FavoriteCharacterDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val favoriteCharacterDelegate: FavoriteCharacterDelegate,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel(),
    FavoriteCharacterDelegate by favoriteCharacterDelegate {

    val pagingCharacterFlow: Flow<PagingData<Characters>> =
        Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { characterRepository.charactersPagingSource() }
        ).flow
        .cachedIn(viewModelScope)
        .flowOn(ioDispatcher)
}