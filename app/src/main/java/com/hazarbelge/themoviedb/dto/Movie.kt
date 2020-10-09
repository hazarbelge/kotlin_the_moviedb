package com.hazarbelge.themoviedb.dto

data class Movie (val popularity: Double,
                  val overview: String,
                  val poster_path: String,
                  val release_date: String,
                  val title: String,
                  val vote_average: Double,
                  val vote_count: Int,
                  val video: Boolean,
                  val id: Int,
                  val adult: Boolean,
                  val backdrop_path: String,
                  val original_language: String,
                  val original_title: String,
                  val genre_ids: List<Int>)

data class Movies(val results: List<Movie>)