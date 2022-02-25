package com.hazarbelge.themoviedb.model

import com.google.gson.annotations.SerializedName

data class ServerErrorModel(
    @SerializedName("is_success")
    var is_success: Boolean?,
    @SerializedName("data")
    var errors: ErrorDescriptions,
    @SerializedName("message")
    var message: String?,
    @SerializedName("code")
    var code: Int?
) {
    data class ErrorDescriptions(
        @SerializedName("lastname")
        var lastname: List<String>?,
        @SerializedName("firstname")
        var firstname: List<String>?,
        @SerializedName("email")
        var email: List<String>?,
        @SerializedName("password")
        var password: List<String>?,
        @SerializedName("nickname")
        var nickname: List<String>?
    )
}