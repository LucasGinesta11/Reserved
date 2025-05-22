package com.example.reserved.data.repository

import com.example.reserved.data.remote.AuthResponse
import com.example.reserved.data.remote.LoginRequest
import com.example.reserved.data.remote.RegisterRequest
import com.example.reserved.data.remote.RetrofitInstance
import retrofit2.Response

object UserRepository {

    suspend fun login(username: String, password: String): Response<AuthResponse> {
        return RetrofitInstance.getApi().login(LoginRequest(username, password))
    }

    suspend fun register(username: String, password: String, email: String, phone: Int): Response<AuthResponse> {
        return RetrofitInstance.getApi().register(RegisterRequest(username, password, email, phone))
    }
}