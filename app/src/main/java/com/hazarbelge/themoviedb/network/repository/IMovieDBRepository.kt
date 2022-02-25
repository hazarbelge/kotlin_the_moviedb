package com.hazarbelge.themoviedb.network.repository

import com.hazarbelge.themoviedb.model.Cast
import com.hazarbelge.themoviedb.model.Crew
import com.hazarbelge.themoviedb.model.Movie
import com.hazarbelge.themoviedb.model.Movies
import com.hazarbelge.themoviedb.model.Result

interface IMovieDBRepository {
    suspend fun getLatestMovie() : Result<Movie>
    suspend fun getNowPlayingMovies() : Result<Movies>
    suspend fun getPopularMovies() : Result<Movies>
    suspend fun getTopRatedMovies() : Result<Movies>
    suspend fun getUpcomingMovies() : Result<Movies>
    suspend fun getMovieById(movieID: String) : Result<Movie>
    suspend fun getCast(movieID: String) : Result<Cast>
    suspend fun getCrew(movieID: String) : Result<Crew>
}