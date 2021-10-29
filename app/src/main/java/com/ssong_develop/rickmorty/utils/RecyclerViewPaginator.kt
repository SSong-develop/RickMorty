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

        val layoutManager = recyclerView.layoutManager

        val lastVisibleItem =
            (layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition()

        if (layoutManager.itemCount <= lastVisibleItem + SPAN_COUNT) {
            loadMore()
        }
    }

    companion object {
        private const val SPAN_COUNT = 2
    }
}