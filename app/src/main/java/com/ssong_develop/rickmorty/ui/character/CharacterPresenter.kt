package com.ssong_develop.rickmorty.ui.character

import com.ssong_develop.rickmorty.di.MainDispatcher
import com.ssong_develop.rickmorty.repository.CharacterRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterPresenter @Inject constructor(
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    private val repository: CharacterRepository,
    private val view: CharacterContract.View
) : CharacterContract.Presenter {

    override var currentPage: Int = 1

    private var job: Job? = null

    private var loading = true

    override fun loadCharacters() {
        job = Job()
        val uiScope = CoroutineScope(mainDispatcher + job!!)
        uiScope.launch {
            val characters = repository.loadCharacters(currentPage,
                onStart = { loading = true },
                onComplete = { loading = false }
            )
            view.showCharacters(characters)
        }
    }

    override fun morePage() {
        currentPage++
        loadCharacters()
    }

    override fun resetPage() {
        currentPage = 1
        loadCharacters()
    }

    override fun setPage(page: Int) {
        currentPage = page
        loadCharacters()
    }
}