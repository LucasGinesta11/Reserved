package com.example.reserved.data.repository

import com.example.reserved.data.remote.network.RetrofitInstance
import com.example.reserved.data.remote.request.LoginRequest
import com.example.reserved.data.remote.request.RegisterRequest
import com.example.reserved.data.remote.response.AuthResponse
import retrofit2.Response

class AuthRepository {
    private val api = RetrofitInstance.getApi()  // Sin token

    suspend fun login(username: String, password: String): Response<AuthResponse> {
        return api.login(LoginRequest(username, password))
    }

    suspend fun register(
        username: String,
        password: String,
        email: String,
        phone: Int
    ): Response<AuthResponse> {
        return api.register(RegisterRequest(username, password, email, phone))
    }
}
