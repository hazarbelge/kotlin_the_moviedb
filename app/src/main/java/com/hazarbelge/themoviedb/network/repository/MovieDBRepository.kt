package com.hazarbelge.themoviedb.network.repository

import com.hazarbelge.themoviedb.network.MovieDBService
import com.hazarbelge.themoviedb.network.utils.call
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieDBRepository(private val movieDBService: MovieDBService) : IMovieDBRepository {
    override suspend fun getMovies(key: String, language: String) = withContext(Dispatchers.IO) {
        call { movieDBService.getMovies(key, language) }.apply {
            return@withContext this
        }
    }

    override suspend fun getMovieById(movieID: String?, key: String, language: String) = withContext(Dispatchers.IO) {
        call { movieDBService.getMovieById(movieID, key, language) }.apply {
            return@withContext this
        }
    }

    override suspend fun getCast(movieID: String?, key: String, language: String) = withContext(Dispatchers.IO) {
        call { movieDBService.getCast(movieID, key, language) }.apply {
            return@withContext this
        }
    }

    override suspend fun getCrew(movieID: String?, key: String, language: String) = withContext(Dispatchers.IO) {
        call { movieDBService.getCrew(movieID, key, language) }.apply {
            return@withContext this
        }
    }
}