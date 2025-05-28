package com.example.reserved.data.repository

import com.example.reserved.data.remote.RetrofitInstance
import com.example.reserved.data.remote.dto.request.LoginRequest
import com.example.reserved.data.remote.dto.request.RegisterRequest
import com.example.reserved.data.remote.dto.response.AuthResponse
import retrofit2.Response

class AuthRepository {
    private val api = RetrofitInstance.getApi()

    suspend fun login(username: String, password: String): Response<AuthResponse> {
        return api.login(LoginRequest(username, password))
    }

    suspend fun register(
        username: String,
        password: String,
        email: String,
        phone: String
    ): Response<AuthResponse> {
        return api.register(RegisterRequest(username, password, email, phone))
    }
}
