package com.hazarbelge.themoviedb.network

import com.hazarbelge.themoviedb.dto.Cast
import com.hazarbelge.themoviedb.dto.Crew
import com.hazarbelge.themoviedb.dto.Movie
import com.hazarbelge.themoviedb.dto.Movies
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiInterface {

    @GET("/3/movie/now_playing")
    fun getMovies(@Query("api_key") key: String, @Query("language") language: String) : Call<Movies>

    @GET("/3/movie/{movieID}")
    fun getProfiles(@Path("movieID") movieID: String?, @Query("api_key") key: String, @Query("language") language: String) : Call<Movie>

    @GET("/3/movie/{movieID}/credits")
    fun getCast(@Path("movieID") movieID: String?, @Query("api_key") key: String, @Query("language") language: String) : Call<Cast>

    @GET("/3/movie/{movieID}/credits")
    fun getCrew(@Path("movieID") movieID: String?, @Query("api_key") key: String, @Query("language") language: String) : Call<Crew>

    companion object {

        var BASE_URL = "https://api.themoviedb.org/"

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}