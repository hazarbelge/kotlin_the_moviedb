package com.hazarbelge.themoviedb.common

import android.content.Context
import android.widget.Toast

class ErrorUtils(private val context: Context) {
    fun errorMessage(message: String?) = Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}