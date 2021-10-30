package com.ssong_develop.rickmorty.ui.detail

import com.ssong_develop.rickmorty.entities.Characters
import com.ssong_develop.rickmorty.entities.Episode

interface CharacterDetailContract {

    interface CharacterDetailView {

        fun showEpisodes(list : List<Episode>)

        fun showEpisodeLoading()

        fun hideEpisodeLoading()

    }

    interface CharacterDetailPresenter {

        var character : Characters

        fun loadEpisode()

    }
}