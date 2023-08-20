package com.ssong_develop.feature_character.presentation.character.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.ssong_develop.feature_character.R
import com.ssong_develop.feature_character.presentation.character.viewholders.LoadStateViewHolder
import com.ssong_develop.feature_character.databinding.ItemLoadStateBinding

internal class FooterLoadStateAdapter : LoadStateAdapter<LoadStateViewHolder>() {
    override fun onBindViewHolder(
        holder: LoadStateViewHolder,
        loadState: LoadState
    ) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadStateViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemLoadStateBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_load_state, parent, false)
        return LoadStateViewHolder(
            binding
        )
    }
}