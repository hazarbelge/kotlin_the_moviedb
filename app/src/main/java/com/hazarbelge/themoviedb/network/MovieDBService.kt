package com.hazarbelge.themoviedb.network

import com.hazarbelge.themoviedb.model.*
import retrofit2.Response
import retrofit2.http.*

interface MovieDBService {
    @GET("movie/latest")
    suspend fun getLatestMovie(
        @Query("api_key") key: String = API_KEY,
        @Query("language") language: String = LANGUAGE,
    ): Response<Movie>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") key: String = API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int,
    ): Response<MovieWrapper>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") key: String = API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int,
    ): Response<MovieWrapper>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") key: String = API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int,
    ): Response<MovieWrapper>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") key: String = API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int,
    ): Response<MovieWrapper>

    @GET("movie/{movieID}")
    suspend fun getMovieById(
        @Path("movieID") movieID: String?,
        @Query("api_key") key: String = API_KEY,
        @Query("language") language: String = LANGUAGE,
    ): Response<Movie>

    @GET("movie/{movieID}/credits")
    suspend fun getCast(
        @Path("movieID") movieID: String?,
        @Query("api_key") key: String = API_KEY,
        @Query("language") language: String = LANGUAGE,
    ): Response<Cast>

    @GET("movie/{movieID}/credits")
    suspend fun getCrew(
        @Path("movieID") movieID: String?,
        @Query("api_key") key: String = API_KEY,
        @Query("language") language: String = LANGUAGE,
    ): Response<Crew>
}