package com.hazarbelge.themoviedb.network.repository

import com.hazarbelge.themoviedb.model.*

interface IMovieDBRepository {
    suspend fun getLatestMovie() : Result<Movie>
    suspend fun getNowPlayingMovies(page: Int) : Result<MovieWrapper>
    suspend fun getPopularMovies(page: Int) : Result<MovieWrapper>
    suspend fun getTopRatedMovies(page: Int) : Result<MovieWrapper>
    suspend fun getUpcomingMovies(page: Int) : Result<MovieWrapper>
    suspend fun getMovieById(movieID: String) : Result<Movie>
    suspend fun getCast(movieID: String) : Result<Cast>
    suspend fun getCrew(movieID: String) : Result<Crew>
}