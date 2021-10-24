package com.ssong_develop.rickmorty.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ssong_develop.rickmorty.entities.Characters
import com.ssong_develop.rickmorty.entities.Episode
import com.ssong_develop.rickmorty.ui.adapters.CharacterEpisodeAdapter
import com.ssong_develop.rickmorty.ui.adapters.CharacterListAdapter
import com.ssong_develop.rickmorty.ui.character.CharacterViewModel
import com.ssong_develop.rickmorty.utils.RecyclerViewPaginator

object RecyclerViewBinding {

    @JvmStatic
    @BindingAdapter("episodeAdapter")
    fun bindEpisodeAdapter(view : RecyclerView, adapter : CharacterEpisodeAdapter){
        view.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter("submitEpisodes")
    fun bindEpisodes(view : RecyclerView , items : List<Episode>){
        (view.adapter as? CharacterEpisodeAdapter)?.submitEpisodes(items)
    }

    @JvmStatic
    @BindingAdapter("paginationCharacterList")
    fun paginationCharacterList(view: RecyclerView, viewModel: CharacterViewModel) {
        RecyclerViewPaginator(
            recyclerView = view,
            loadMore = { viewModel.morePage() }
        )
    }

    @JvmStatic
    @BindingAdapter("submitList")
    fun RecyclerView.setCharacterItems(list: List<Characters>?) {
        (adapter as? CharacterListAdapter)?.run {
            submitList(list)
        }
    }
}