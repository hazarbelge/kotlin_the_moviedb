package com.hazarbelge.themoviedb.util

import android.view.View

interface ItemClickListener<T> {
    fun onItemClicked(v: View, data: T)
}