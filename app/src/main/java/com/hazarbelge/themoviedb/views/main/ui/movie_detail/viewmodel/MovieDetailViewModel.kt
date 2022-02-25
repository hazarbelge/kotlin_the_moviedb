package com.hazarbelge.themoviedb.views.main.ui.movie_detail.viewmodel

import androidx.lifecycle.liveData
import com.hazarbelge.themoviedb.network.repository.IMovieDBRepository
import com.hazarbelge.themoviedb.base.BaseViewModel

class MovieDetailViewModel(private val repository: IMovieDBRepository) : BaseViewModel() {
    fun getMovieById(movieID: String) = liveData {
        emit(repository.getMovieById(movieID))
    }

    fun getCast(movieID: String) = liveData {
        emit(repository.getCast(movieID))
    }

    fun getCrew(movieID: String) = liveData {
        emit(repository.getCrew(movieID))
    }
}