package com.ssong_develop.feature_search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.ssong_develop.core_model.RickMortyCharacter
import com.ssong_develop.feature_search.R
import com.ssong_develop.feature_search.SearchItemClickDelegate
import com.ssong_develop.feature_search.databinding.ItemSearchResultBinding
import com.ssong_develop.feature_search.viewholder.SearchResultViewHolder

private val searchResultDiffItemCallback = object : DiffUtil.ItemCallback<RickMortyCharacter>() {
    override fun areItemsTheSame(
        oldItem: RickMortyCharacter,
        newItem: RickMortyCharacter
    ): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: RickMortyCharacter,
        newItem: RickMortyCharacter
    ): Boolean =
        oldItem == newItem
}

class SearchResultPagingAdapter(
    private val delegate: SearchItemClickDelegate
) : PagingDataAdapter<RickMortyCharacter, SearchResultViewHolder>(
    searchResultDiffItemCallback
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemSearchResultBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_search_result, parent, false)
        return SearchResultViewHolder(binding, delegate)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}