package com.hazarbelge.themoviedb.common

import android.view.View

interface ItemClickListener<T> {
    fun onItemClicked(v: View, data: T)
}