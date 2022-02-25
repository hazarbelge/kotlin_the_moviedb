package com.hazarbelge.themoviedb.views.main.views.latest.viewmodel

import androidx.lifecycle.liveData
import com.hazarbelge.themoviedb.network.repository.IMovieDBRepository
import com.hazarbelge.themoviedb.base.BaseViewModel

class LatestViewModel(private val repository: IMovieDBRepository) : BaseViewModel() {
    fun getLatestMovie() = liveData {
        emit(repository.getLatestMovie())
    }
}