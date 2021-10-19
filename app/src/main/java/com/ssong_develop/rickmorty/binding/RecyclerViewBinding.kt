package com.ssong_develop.rickmorty.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.rickmorty.entities.Characters
import com.ssong_develop.rickmorty.ui.adapters.CharacterListAdapter
import com.ssong_develop.rickmorty.ui.character.CharacterViewModel
import com.ssong_develop.rickmorty.utils.RecyclerViewPaginator

object RecyclerViewBinding {

    @JvmStatic
    @BindingAdapter("paginationCharacterList")
    fun paginationCharacterList(view : RecyclerView, viewModel : CharacterViewModel){
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