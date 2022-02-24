package com.hazarbelge.themoviedb.network.utils

import android.util.Log
import com.hazarbelge.themoviedb.network.model.Result
import com.hazarbelge.themoviedb.network.model.ServerErrorModel
import com.google.gson.Gson
import com.hazarbelge.themoviedb.network.model.Movie
import com.hazarbelge.themoviedb.network.model.Movies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

suspend fun <T : Any> call(call: suspend () -> Response<T>): Result<T> = try {
    val response = call.invoke()
    if (response.isSuccessful) {
        Result.Success(response.body()!!)
    } else {
        var apiError: ServerErrorModel? = null
        coroutineScope {
            withContext(Dispatchers.IO) {
                try {
                    apiError = response.errorBody()?.string()?.let {
                        Gson().fromJson(it, ServerErrorModel::class.java)
                    }
                } catch (ex: IOException) {
                    Result.Failure(ex.localizedMessage ?: "")
                    Log.i("IOException", "IOException is $ex")
                }
            }
        }
        apiError?.code = response.code()
        apiError?.let { Result.ErrorWithBody(it) } ?: Result.Error(response.message())
    }

} catch (e: Exception) {
    Result.Failure(e.localizedMessage ?: e.toString())
}