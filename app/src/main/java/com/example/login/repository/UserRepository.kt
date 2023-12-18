package com.example.login.repository

import com.example.login.interfaceApi.UserApi
import com.example.login.response.BalaceResponse
import com.example.login.response.BalanceRequest
import com.example.login.response.LoginRequest
import com.example.login.response.LoginResponse
import retrofit2.Call
import retrofit2.Response

class UserRepository {

    suspend fun loginUser(loginRequest: LoginRequest): Response<LoginResponse>? {
        return  UserApi.getApi()?.loginUser(loginRequest = loginRequest)
    }

    suspend fun balanceUser(userId: String): Response<BalaceResponse>? {
        return UserApi.getApi()?.balance(userId)
    }
}