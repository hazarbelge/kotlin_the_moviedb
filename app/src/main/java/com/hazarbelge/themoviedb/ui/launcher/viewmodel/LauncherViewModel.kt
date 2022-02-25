package com.hazarbelge.themoviedb.ui.launcher.viewmodel

import com.hazarbelge.themoviedb.network.repository.IMovieDBRepository
import com.hazarbelge.themoviedb.base.BaseViewModel

class LauncherViewModel(private val repository: IMovieDBRepository) : BaseViewModel() {
    //val getSessionID = liveData { emit(repository.getSessionID()) }
}