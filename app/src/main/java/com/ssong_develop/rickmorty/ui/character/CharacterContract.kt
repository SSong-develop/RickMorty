package com.ssong_develop.rickmorty.ui.character

import android.view.View
import com.ssong_develop.rickmorty.entities.Characters

interface CharacterContract {

    interface CharactersView {

        fun showCharacters(list: List<Characters>)

        fun showLoading()

        fun hideLoading()

        fun showCharacterDetail(itemView : View, character : Characters)

    }

    interface CharactersPresenter {

        var currentPage: Int

        fun loadCharacters()

        fun morePage()

        fun resetPage()

        fun setPage(page: Int)

        fun openCharacterDetail(itemView : View, character : Characters)

    }
}