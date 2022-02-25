package com.hazarbelge.themoviedb.views.main.dependency_injection

import com.hazarbelge.themoviedb.views.main.views.home.viewmodel.HomeViewModel
import com.hazarbelge.themoviedb.views.main.views.movie_detail.viewmodel.MovieDetailViewModel
import com.hazarbelge.themoviedb.views.main.views.latest.viewmodel.LatestViewModel
import com.hazarbelge.themoviedb.views.main.views.now_playing.viewmodel.NowPlayingViewModel
import com.hazarbelge.themoviedb.views.main.views.popular.viewmodel.PopularViewModel
import com.hazarbelge.themoviedb.views.main.views.top_rated.viewmodel.TopRatedViewModel
import com.hazarbelge.themoviedb.views.main.views.upcoming.viewmodel.UpcomingViewModel
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