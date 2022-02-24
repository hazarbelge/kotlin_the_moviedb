package com.hazarbelge.themoviedb.ui.main.views.now_playing.viewmodel

import androidx.lifecycle.liveData
import com.hazarbelge.themoviedb.network.repository.IMovieDBRepository
import com.hazarbelge.themoviedb.base.BaseViewModel

class NowPlayingViewModel(private val repository: IMovieDBRepository) : BaseViewModel() {
    fun getMovies(key: String, language: String) = liveData {
        emit(repository.getMovies(key, language))
    }
}