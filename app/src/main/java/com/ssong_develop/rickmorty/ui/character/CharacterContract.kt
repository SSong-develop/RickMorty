package com.ssong_develop.rickmorty.ui.character

import com.ssong_develop.rickmorty.entities.Characters

interface CharacterContract {

    interface View {

        fun showCharacters(list: List<Characters>)

        fun showLoading()

        fun hideLoading()

    }

    interface Presenter {

        var currentPage: Int

        fun loadCharacters()

        fun morePage()

        fun resetPage()

        fun setPage(page: Int)

    }
}