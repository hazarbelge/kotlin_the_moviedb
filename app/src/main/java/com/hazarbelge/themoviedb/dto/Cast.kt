package com.hazarbelge.themoviedb.dto

/**
 * We created 4 data class. Actor and Staff data classes contain informations about the actors or staff, beside this the Cast and Crew
 * data classes have immutable variables and they keep the list of actors or employees.
 */
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