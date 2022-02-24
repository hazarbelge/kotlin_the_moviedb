package com.hazarbelge.themoviedb.network

import com.hazarbelge.themoviedb.network.model.Cast
import com.hazarbelge.themoviedb.network.model.Crew
import com.hazarbelge.themoviedb.network.model.Movie
import com.hazarbelge.themoviedb.network.model.Movies
import retrofit2.Response
import retrofit2.http.*

interface MovieDBService {
    @GET("movie/now_playing")
    suspend fun getMovies(@Query("api_key") key: String, @Query("language") language: String) : Response<Movies>

    @GET("movie/{movieID}")
    suspend fun getMovieById(@Path("movieID") movieID: String?, @Query("api_key") key: String, @Query("language") language: String) : Response<Movie>

    @GET("movie/{movieID}/credits")
    suspend fun getCast(@Path("movieID") movieID: String?, @Query("api_key") key: String, @Query("language") language: String) : Response<Cast>

    @GET("movie/{movieID}/credits")
    suspend fun getCrew(@Path("movieID") movieID: String?, @Query("api_key") key: String, @Query("language") language: String) : Response<Crew>
}