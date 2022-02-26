package com.hazarbelge.themoviedb.ui.main.dependency_injection

import com.hazarbelge.themoviedb.ui.main.views.home.viewmodel.HomeViewModel
import com.hazarbelge.themoviedb.ui.main.views.movie_detail.viewmodel.MovieDetailViewModel
import com.hazarbelge.themoviedb.ui.main.views.latest.viewmodel.LatestViewModel
import com.hazarbelge.themoviedb.ui.main.views.now_playing.viewmodel.NowPlayingViewModel
import com.hazarbelge.themoviedb.ui.main.views.popular.viewmodel.PopularViewModel
import com.hazarbelge.themoviedb.ui.main.views.top_rated.viewmodel.TopRatedViewModel
import com.hazarbelge.themoviedb.ui.main.views.upcoming.viewmodel.UpcomingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel { HomeViewModel() }
    viewModel { LatestViewModel(get()) }
    viewModel { NowPlayingViewModel(get()) }
    viewModel { PopularViewModel(get()) }
    viewModel { TopRatedViewModel(get()) }
    viewModel { UpcomingViewModel(get()) }
    viewModel { MovieDetailViewModel(get()) }
}