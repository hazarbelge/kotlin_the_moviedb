package com.hazarbelge.themoviedb.network.repository

import com.hazarbelge.themoviedb.network.model.Cast
import com.hazarbelge.themoviedb.network.model.Crew
import com.hazarbelge.themoviedb.network.model.Movie
import com.hazarbelge.themoviedb.network.model.Movies
import com.hazarbelge.themoviedb.network.model.Result

interface IMovieDBRepository {
    suspend fun getMovies(key: String, language: String) : Result<Movies>
    suspend fun getMovieById(movieID: String?, key: String, language: String) : Result<Movie>
    suspend fun getCast(movieID: String?, key: String, language: String) : Result<Cast>
    suspend fun getCrew(movieID: String?, key: String, language: String) : Result<Crew>
}