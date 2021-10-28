package com.ssong_develop.rickmorty.ui.character

import com.ssong_develop.rickmorty.di.MainDispatcher
import com.ssong_develop.rickmorty.repository.CharacterRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class CharacterPresenter @Inject constructor(
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    private val repository: CharacterRepository,
    private val view: CharacterContract.View
) : CharacterContract.Presenter {

    override var currentPage: Int = 0

    private var job: Job? = null

    private var loading = true

    override fun loadCharacters() {
        job = Job()
        val uiScope = CoroutineScope(mainDispatcher + job!!)
        uiScope.launch {
            val characters = repository.loadCharacters(0,
                onStart = { loading = true },
                onComplete = { loading = false }
            )
            view.showCharacters(characters)
        }
    }

}