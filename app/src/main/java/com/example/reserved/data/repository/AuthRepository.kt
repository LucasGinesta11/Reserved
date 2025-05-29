package com.example.reserved.data.repository

import com.example.reserved.data.remote.RetrofitInstance
import com.example.reserved.data.remote.dto.request.LoginRequest
import com.example.reserved.data.remote.dto.request.RegisterRequest
import com.example.reserved.data.remote.dto.response.AuthResponse
import com.example.reserved.data.session.SessionManager
import retrofit2.Response

class AuthRepository {
    private val api = RetrofitInstance.getApi()

    private var authToken: String? = null

    suspend fun login(username: String, password: String): Response<AuthResponse> {
        val response = api.login(LoginRequest(username, password))
        if (response.isSuccessful) {
            authToken = response.body()?.token }
        return response
    }

//    suspend fun logout(): Response<Unit> {
//        val response = api.logout()
//        if (response.isSuccessful) {
//            SessionManager.clear()  // Limpias token y userId
//            RetrofitInstance.reset() // Opcional: reconstruir retrofit para limpiar interceptores y token
//        }
//        return response
//    }


    suspend fun register(
        username: String,
        password: String,
        email: String,
        phone: String
    ): Response<AuthResponse> {
        return api.register(RegisterRequest(username, password, email, phone))
    }
}
