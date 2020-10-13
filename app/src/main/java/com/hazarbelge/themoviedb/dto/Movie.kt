package com.hazarbelge.themoviedb.dto

/*We created 2 data class. Movie data class contains informations about the movie and the Movies data class has an immutable variable and its type
* is a Movie List*/
data class Movie (val overview: String,
                  val poster_path: String,
                  val release_date: String,
                  val title: String,
                  val vote_average: Double,
                  val id: String,
                  val backdrop_path: String,
                  val tagline: String,
                  val runtime: String,
                  val genres: List<Map<String,String>>,
                  val production_countries: List<Map<String,String>>,
                  val status: String)

data class Movies(val results: List<Movie>)