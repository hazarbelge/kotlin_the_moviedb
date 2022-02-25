package com.hazarbelge.themoviedb.ui.main.views.top_rated.viewmodel

import androidx.lifecycle.liveData
import com.hazarbelge.themoviedb.network.repository.IMovieDBRepository
import com.hazarbelge.themoviedb.base.BaseViewModel

class TopRatedViewModel(private val repository: IMovieDBRepository) : BaseViewModel() {
    fun getTopRatedMovies(page: Int) = liveData {
        emit(repository.getTopRatedMovies(page = page))
    }
}