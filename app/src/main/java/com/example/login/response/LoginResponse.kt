package com.example.login.response
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("code")
    var code: Int,
    @SerializedName("data")
    var `data`: Data,
    @SerializedName("message")
    var message: String
) {
    data class Data(
        @SerializedName("email")
        var email: String,
        @SerializedName("userId")
        var id: String,
        @SerializedName("username")
        var username: String,
        @SerializedName("name")
        var name: String,
        @SerializedName("accessToken")
        var token: String
    )
}