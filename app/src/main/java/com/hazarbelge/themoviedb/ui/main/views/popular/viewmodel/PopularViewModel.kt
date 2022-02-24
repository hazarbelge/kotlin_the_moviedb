package com.hazarbelge.themoviedb.ui.main.views.popular.viewmodel

import androidx.lifecycle.liveData
import com.hazarbelge.themoviedb.network.repository.IMovieDBRepository
import com.hazarbelge.themoviedb.base.BaseViewModel

class PopularViewModel(private val repository: IMovieDBRepository) : BaseViewModel() {
    fun getPopularMovies() = liveData {
        emit(repository.getPopularMovies())
    }
}