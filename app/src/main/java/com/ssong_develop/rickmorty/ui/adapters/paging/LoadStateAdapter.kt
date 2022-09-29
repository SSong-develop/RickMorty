package com.ssong_develop.rickmorty.ui.adapters.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.ssong_develop.rickmorty.R
import com.ssong_develop.rickmorty.databinding.ItemLoadStateBinding
import com.ssong_develop.rickmorty.ui.viewholders.LoadStateViewHolder

class FooterLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoadStateViewHolder>() {
    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemLoadStateBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_load_state, parent, false)
        return LoadStateViewHolder(binding, retry)
    }
}