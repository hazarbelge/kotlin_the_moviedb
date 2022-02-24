package com.hazarbelge.themoviedb.ui.main.dependency_injection

import com.hazarbelge.themoviedb.ui.main.views.home.viewmodel.HomeViewModel
import com.hazarbelge.themoviedb.ui.main.views.movie_detail.viewmodel.MovieDetailViewModel
import com.hazarbelge.themoviedb.ui.main.views.now_playing.viewmodel.NowPlayingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel { HomeViewModel() }
    viewModel { NowPlayingViewModel(get()) }
    viewModel { MovieDetailViewModel(get()) }
}