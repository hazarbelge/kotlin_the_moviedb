package com.hazarbelge.themoviedb.views.main.ui.upcoming.viewmodel

import androidx.lifecycle.liveData
import com.hazarbelge.themoviedb.network.repository.IMovieDBRepository
import com.hazarbelge.themoviedb.base.BaseViewModel

class UpcomingViewModel(private val repository: IMovieDBRepository) : BaseViewModel() {
    fun getUpcomingMovies() = liveData {
        emit(repository.getUpcomingMovies())
    }
}