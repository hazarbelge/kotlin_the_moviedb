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

    /*With GET parameter we receive the movies from this url. We need an API key to use their API and we put it with Query parameter.
    * After that, we set its language. This method returns a response which contains endpoints about the movies.*/
    @GET("/3/movie/now_playing")
    fun getMovies(@Query("api_key") key: String, @Query("language") language: String) : Call<Movies>

    /*This method returns endpoints about a specific movie. It takes an id of the movie and with Path parameter it sends the id to url.*/
    @GET("/3/movie/{movieID}")
    fun getProfiles(@Path("movieID") movieID: String?, @Query("api_key") key: String, @Query("language") language: String) : Call<Movie>

    /*getCast and getCrew methods are the same but one difference, return values. getCast receives the actors and getCrew receives the employees*/
    @GET("/3/movie/{movieID}/credits")
    fun getCast(@Path("movieID") movieID: String?, @Query("api_key") key: String, @Query("language") language: String) : Call<Cast>

    @GET("/3/movie/{movieID}/credits")
    fun getCrew(@Path("movieID") movieID: String?, @Query("api_key") key: String, @Query("language") language: String) : Call<Crew>

    companion object {

        private var BASE_URL = "https://api.themoviedb.org/"

        /*This method is where we create a retrofit object, determine its base url and build it. It returns this interface to use the above methods.*/
        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}