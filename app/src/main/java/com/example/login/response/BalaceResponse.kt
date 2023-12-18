package com.example.login.response
import com.google.gson.annotations.SerializedName

data class BalaceResponse(
    @SerializedName("code")
    var code: Int,
    @SerializedName("data")
    var `data`: Data,
    @SerializedName("message")
    var message: String
) {
    data class Data(
        @SerializedName("balance")
        var balance: Int,
        @SerializedName("name")
        var name: String
    )
}