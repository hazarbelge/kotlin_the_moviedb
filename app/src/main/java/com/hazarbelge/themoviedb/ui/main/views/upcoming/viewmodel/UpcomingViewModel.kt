package com.hazarbelge.themoviedb.ui.main.views.upcoming.viewmodel

import androidx.lifecycle.liveData
import com.hazarbelge.themoviedb.network.repository.IMovieDBRepository
import com.hazarbelge.themoviedb.base.BaseViewModel

class UpcomingViewModel(private val repository: IMovieDBRepository) : BaseViewModel() {
    fun getUpcomingMovies(page: Int) = liveData {
        emit(repository.getUpcomingMovies(page = page))
    }
}