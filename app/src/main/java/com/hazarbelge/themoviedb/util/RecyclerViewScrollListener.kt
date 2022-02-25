package com.hazarbelge.themoviedb.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class RecyclerViewScrollListener(private val scrollCallback: ScrollCallback) : RecyclerView.OnScrollListener() {

    private var firstVisibleItem: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    private var visibleThreshold: Int = 10
    private var previousTotal: Int = 0

    private var loading = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount = recyclerView.childCount
        totalItemCount = recyclerView.layoutManager!!.itemCount

        firstVisibleItem = when (recyclerView.layoutManager) {
            is StaggeredGridLayoutManager -> (recyclerView.layoutManager as StaggeredGridLayoutManager)
                .findFirstCompletelyVisibleItemPositions(null)[0]
            else -> (recyclerView.layoutManager as LinearLayoutManager)
                .findFirstVisibleItemPosition()
        }

        if (loading && totalItemCount > previousTotal) {
            loading = false
            previousTotal = totalItemCount
        }

        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            scrollCallback.onScrollCompleted(firstVisibleItem, false)
            loading = true
        }
    }

    interface ScrollCallback {
        fun onScrollCompleted(firstVisibleItem: Int, isLoadingMoreData: Boolean)
    }
}