package com.hazarbelge.themoviedb.views.main.dependency_injection

import com.hazarbelge.themoviedb.views.main.ui.home.viewmodel.HomeViewModel
import com.hazarbelge.themoviedb.views.main.ui.movie_detail.viewmodel.MovieDetailViewModel
import com.hazarbelge.themoviedb.views.main.ui.latest.viewmodel.LatestViewModel
import com.hazarbelge.themoviedb.views.main.ui.now_playing.viewmodel.NowPlayingViewModel
import com.hazarbelge.themoviedb.views.main.ui.popular.viewmodel.PopularViewModel
import com.hazarbelge.themoviedb.views.main.ui.top_rated.viewmodel.TopRatedViewModel
import com.hazarbelge.themoviedb.views.main.ui.upcoming.viewmodel.UpcomingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel { HomeViewModel() }
    viewModel { LatestViewModel(get()) }
    viewModel { NowPlayingViewModel(get()) }
    viewModel { PopularViewModel(get()) }
    viewModel { TopRatedViewModel(get()) }
    viewModel { UpcomingViewModel(get()) }
    viewModel { MovieDetailViewModel(get()) }
}