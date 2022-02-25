package com.hazarbelge.themoviedb.ui.error_message

import android.content.Context
import android.widget.Toast

class ErrorMessage(private val context: Context) {
    fun errorMessage(message: String?) = Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}