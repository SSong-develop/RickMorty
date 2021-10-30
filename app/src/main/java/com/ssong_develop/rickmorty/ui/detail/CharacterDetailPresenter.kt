package com.ssong_develop.rickmorty.ui.detail

import com.ssong_develop.rickmorty.di.MainDispatcher
import com.ssong_develop.rickmorty.entities.Characters
import com.ssong_develop.rickmorty.repository.CharacterRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class CharacterDetailPresenter @Inject constructor(
    @MainDispatcher private val mainDispatcher : CoroutineDispatcher,
    private val repository : CharacterRepository
) : CharacterDetailContract.CharacterDetailPresenter {

    override var character: Characters
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun loadEpisode() {
        TODO("Not yet implemented")
    }

}