package com.ssong_develop.rickmorty.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.core_common.Resource
import com.ssong_develop.core_model.Characters
import com.ssong_develop.core_model.Episode
import com.ssong_develop.rickmorty.ui.adapters.CharacterEpisodeAdapter
import com.ssong_develop.rickmorty.ui.adapters.CharacterListAdapter

object RecyclerViewBinding {

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
}