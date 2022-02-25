package com.hazarbelge.themoviedb.model

sealed class Result<out T: Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: String) : Result<Nothing>()
    data class ErrorWithBody(val exception: ServerErrorModel) : Result<Nothing>()
    data class Failure(val exception: String) : Result<Nothing>()

}