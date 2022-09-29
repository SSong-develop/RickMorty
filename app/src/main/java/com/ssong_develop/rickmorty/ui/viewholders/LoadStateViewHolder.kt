package com.ssong_develop.rickmorty.ui.viewholders

import android.view.View
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.rickmorty.databinding.ItemLoadStateBinding

class LoadStateViewHolder(
    private val binding : ItemLoadStateBinding,
    retry : () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(loadState: LoadState) {
        when (loadState) {
            is LoadState.Error -> {

            }
            is LoadState.Loading -> {
                binding.test.visibility = View.VISIBLE
            }
            is LoadState.NotLoading -> {
                binding.test.visibility = View.GONE
            }
        }
    }
}