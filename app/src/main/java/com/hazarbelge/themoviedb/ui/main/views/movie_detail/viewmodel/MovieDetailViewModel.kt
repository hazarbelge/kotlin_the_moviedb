package com.hazarbelge.themoviedb.ui.main.views.movie_detail.viewmodel

import androidx.lifecycle.liveData
import com.hazarbelge.themoviedb.network.repository.IMovieDBRepository
import com.hazarbelge.themoviedb.base.BaseViewModel

class MovieDetailViewModel(private val repository: IMovieDBRepository) : BaseViewModel() {
    fun getMovieById(movieID: String?, key: String, language: String) = liveData {
        emit(repository.getMovieById(movieID, key, language))
    }

    fun getCast(movieID: String?, key: String, language: String) = liveData {
        emit(repository.getCast(movieID, key, language))
    }

    fun getCrew(movieID: String?, key: String, language: String) = liveData {
        emit(repository.getCrew(movieID, key, language))
    }
}