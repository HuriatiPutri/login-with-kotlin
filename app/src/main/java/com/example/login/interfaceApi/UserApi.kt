package com.example.login.interfaceApi

import com.example.login.client.ApiClient
import com.example.login.response.BalaceResponse
import com.example.login.response.LoginRequest
import com.example.login.response.LoginResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface UserApi {

    @POST("/.netlify/functions/api/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("/.netlify/functions/api/balance")
    suspend fun balance(@Query("userId") userId: String): Response<BalaceResponse>

    companion object {
        fun getApi(): UserApi? {
            return ApiClient.client?.create(UserApi::class.java)
        }
    }
}