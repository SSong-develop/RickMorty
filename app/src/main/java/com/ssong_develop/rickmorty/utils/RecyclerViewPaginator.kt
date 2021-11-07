package com.ssong_develop.rickmorty.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewPaginator(
    recyclerView: RecyclerView,
    private val loadMore: () -> Unit
) : RecyclerView.OnScrollListener() {

    init {
        recyclerView.addOnScrollListener(this)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        recyclerView.layoutManager?.let {
            val totalItemCount = it.itemCount
            val lastVisibleItemPosition =
                (it as GridLayoutManager).findLastCompletelyVisibleItemPosition()

            if ((SPAN_COUNT + lastVisibleItemPosition) >= totalItemCount) {
                loadMore()
            }
        }
    }

    companion object {
        private const val SPAN_COUNT = 2
    }
}