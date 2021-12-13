package com.ssong_develop.rickmorty.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewPaginator(
    recyclerView: RecyclerView,
    private val resetPage : () -> Unit,
    private val loadMore: (Int) -> Unit,
    private val onLast: () -> Boolean
) : RecyclerView.OnScrollListener() {

    var currentPage = 1

    init {
        recyclerView.addOnScrollListener(this)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        recyclerView.layoutManager?.let {
            val totalItemCount = it.itemCount
            val lastVisibleItemPosition =
                (it as GridLayoutManager).findLastCompletelyVisibleItemPosition()

            if ((SPAN_COUNT + lastVisibleItemPosition) >= totalItemCount && dy > 0) {
                if(onLast()){
                    resetPage()
                    resetCurrentPage()
                } else {
                    loadMore(++currentPage)
                }
            }
        }
    }

    private fun resetCurrentPage() {
        this.currentPage = 1
    }

    companion object {
        private const val SPAN_COUNT = 2
    }
}