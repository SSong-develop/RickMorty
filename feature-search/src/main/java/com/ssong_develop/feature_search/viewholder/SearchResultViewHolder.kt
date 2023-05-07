package com.ssong_develop.feature_search.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ssong_develop.core_model.RickMortyCharacter
import com.ssong_develop.feature_search.SearchItemClickDelegate
import com.ssong_develop.feature_search.databinding.ItemSearchResultBinding

class SearchResultViewHolder(
    private val binding: ItemSearchResultBinding,
    private val delegate: SearchItemClickDelegate
) : ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

    private lateinit var character: RickMortyCharacter

    init {
        binding.root.setOnClickListener(this)
        binding.root.setOnLongClickListener(this)
    }

    fun bind(data: RickMortyCharacter) {
        character = data
        binding.apply {
            this.characters = data
            executePendingBindings()
        }
    }

    override fun onClick(view: View) {
        delegate.onItemClick(character)
    }

    override fun onLongClick(view: View): Boolean {
        return false
    }
}