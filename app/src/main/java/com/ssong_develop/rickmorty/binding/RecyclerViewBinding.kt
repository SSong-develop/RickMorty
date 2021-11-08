package com.ssong_develop.rickmorty.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.rickmorty.entities.Characters
import com.ssong_develop.rickmorty.entities.Episode
import com.ssong_develop.rickmorty.vo.Resource
import com.ssong_develop.rickmorty.ui.adapters.CharacterEpisodeAdapter
import com.ssong_develop.rickmorty.ui.adapters.CharacterListAdapter
import com.ssong_develop.rickmorty.ui.character.CharacterViewModel
import com.ssong_develop.rickmorty.utils.RecyclerViewPaginator
import kotlinx.coroutines.ExperimentalCoroutinesApi

object RecyclerViewBinding {

    @JvmStatic
    @BindingAdapter("episodeAdapter")
    fun bindEpisodeAdapter(view: RecyclerView, adapter: CharacterEpisodeAdapter) {
        view.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter("submitEpisodes")
    fun bindEpisodes(view: RecyclerView, items: List<Episode>) {
        (view.adapter as? CharacterEpisodeAdapter)?.submitEpisodes(items)
    }

    @JvmStatic
    @BindingAdapter("submitCharacters")
    fun bindCharacters(view: RecyclerView, items: Resource<List<Characters>>) {
        (view.adapter as? CharacterListAdapter)?.submitList(items.data)
    }

    @ExperimentalCoroutinesApi
    @JvmStatic
    @BindingAdapter("paginationCharacterList")
    fun paginationCharacterList(view: RecyclerView, viewModel: CharacterViewModel) {
        RecyclerViewPaginator(
            recyclerView = view,
            loadMore = { viewModel.morePage() }
        )
    }

    @JvmStatic
    @BindingAdapter("charactersAdapter")
    fun bindCharacterAdapter(view: RecyclerView, adapter: CharacterListAdapter) {
        view.adapter = adapter
    }
}