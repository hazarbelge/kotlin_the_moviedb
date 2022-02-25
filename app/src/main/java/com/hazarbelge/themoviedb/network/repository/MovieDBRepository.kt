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

    override suspend fun getNowPlayingMovies(page: Int) = withContext(Dispatchers.IO) {
        call { movieDBService.getNowPlayingMovies(page = page) }.apply {
            return@withContext this
        }
    }

    override suspend fun getPopularMovies(page: Int) = withContext(Dispatchers.IO) {
        call { movieDBService.getPopularMovies(page = page) }.apply {
            return@withContext this
        }
    }

    override suspend fun getTopRatedMovies(page: Int) = withContext(Dispatchers.IO) {
        call { movieDBService.getTopRatedMovies(page = page) }.apply {
            return@withContext this
        }
    }

    override suspend fun getUpcomingMovies(page: Int) = withContext(Dispatchers.IO) {
        call { movieDBService.getUpcomingMovies(page = page) }.apply {
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