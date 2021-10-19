package com.ssong_develop.rickmorty.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO : Detail하게 좀 적어야 할 거 같습니다.
class RecyclerViewPaginator(
    recyclerView: RecyclerView,
    private val loadMore: (Int) -> Unit
) : RecyclerView.OnScrollListener() {

    var currentPage = 1

    init {
        recyclerView.addOnScrollListener(this)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val layoutManager = recyclerView.layoutManager

        val lastVisibleItem =
            (layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition()

        if (layoutManager.itemCount <= lastVisibleItem + SPAN_COUNT) {
            loadMore(++currentPage)
        }
    }

    companion object {
        private const val SPAN_COUNT = 2
    }
}