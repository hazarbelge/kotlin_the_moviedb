package com.hazarbelge.themoviedb.network.model

data class Movie(
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double,
    val id: String,
    val backdrop_path: String,
    val tagline: String,
    val runtime: String,
    val genres: List<Map<String, String>>,
    val production_countries: List<Map<String, String>>,
    val status: String,
)

data class Movies(val results: List<Movie>)