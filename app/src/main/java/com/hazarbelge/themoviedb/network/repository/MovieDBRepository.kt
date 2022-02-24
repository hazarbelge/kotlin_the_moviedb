package com.hazarbelge.themoviedb.network.repository

import com.hazarbelge.themoviedb.network.MovieDBService
import com.hazarbelge.themoviedb.network.utils.call
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieDBRepository(private val movieDBService: MovieDBService) : IMovieDBRepository {
    override suspend fun getLatestMovie() = withContext(Dispatchers.IO) {
        call { movieDBService.getLatestMovie() }.apply {
            return@withContext this
        }
    }

    override suspend fun getNowPlayingMovies() = withContext(Dispatchers.IO) {
        call { movieDBService.getNowPlayingMovies() }.apply {
            return@withContext this
        }
    }

    override suspend fun getPopularMovies() = withContext(Dispatchers.IO) {
        call { movieDBService.getPopularMovies() }.apply {
            return@withContext this
        }
    }

    override suspend fun getTopRatedMovies() = withContext(Dispatchers.IO) {
        call { movieDBService.getTopRatedMovies() }.apply {
            return@withContext this
        }
    }

    override suspend fun getUpcomingMovies() = withContext(Dispatchers.IO) {
        call { movieDBService.getUpcomingMovies() }.apply {
            return@withContext this
        }
    }

    override suspend fun getMovieById(movieID: String) = withContext(Dispatchers.IO) {
        call { movieDBService.getMovieById(movieID) }.apply {
            return@withContext this
        }
    }

    override suspend fun getCast(movieID: String) = withContext(Dispatchers.IO) {
        call { movieDBService.getCast(movieID) }.apply {
            return@withContext this
        }
    }

    override suspend fun getCrew(movieID: String) = withContext(Dispatchers.IO) {
        call { movieDBService.getCrew(movieID) }.apply {
            return@withContext this
        }
    }
}