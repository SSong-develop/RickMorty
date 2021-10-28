package com.ssong_develop.rickmorty.ui.character

import com.ssong_develop.rickmorty.network.client.Characters

// TODO : divide business logics , and appropriate function names
interface CharacterContract {

    interface View {
        fun showCharacters(list : List<Characters>)
    }

    interface Presenter {

        var currentPage : Int

        fun loadCharacters()

    }
}