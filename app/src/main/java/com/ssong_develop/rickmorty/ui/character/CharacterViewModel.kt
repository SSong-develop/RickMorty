package com.ssong_develop.rickmorty.ui.character

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ssong_develop.core_data.repository.CharacterRepository
import com.ssong_develop.core_model.Characters
import com.ssong_develop.core_common.di.IoDispatcher
import com.ssong_develop.core_data.network.calladapter.common.NetworkResponse
import com.ssong_develop.core_data.repository.NetworkResourceCharacterRepository
import com.ssong_develop.rickmorty.ui.delegate.FavoriteCharacterDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val networkResourceCharacterRepository: NetworkResourceCharacterRepository,
    private val favoriteCharacterDelegate: FavoriteCharacterDelegate,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel(),
    FavoriteCharacterDelegate by favoriteCharacterDelegate {

    val pagingCharacterFlow: Flow<PagingData<Characters>> =
        Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { networkResourceCharacterRepository.getCharacters() }
        ).flow
            .cachedIn(viewModelScope)
            .flowOn(ioDispatcher)

}