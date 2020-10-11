package com.hazarbelge.themoviedb.dto

data class Actor (val cast_id: String,
                  val character: String,
                  val credit_id: String,
                  val gender: String,
                  val id: String,
                  val name: String,
                  val order: String,
                  val profile_path: String)

data class Cast(val cast: List<Actor>)

data class Staff (val credit_id: String,
                  val department: String,
                  val gender: String,
                  val job: String,
                  val id: String,
                  val name: String,
                  val profile_path: String)

data class Crew(val crew: List<Staff>)