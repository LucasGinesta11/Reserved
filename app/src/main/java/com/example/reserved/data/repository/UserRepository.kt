package com.example.reserved.data.repository

import android.util.Log
import com.example.reserved.data.remote.network.RetrofitInstance
import com.example.reserved.data.remote.request.UpdatePasswordRequest
import com.example.reserved.data.remote.request.UpdateUsernameRequest
import com.example.reserved.data.remote.response.AuthResponse
import com.example.reserved.data.session.SessionManager.userId
import retrofit2.Response

class UserRepository(private val token: String) {
    private val api = RetrofitInstance.getApi(token)

    suspend fun updateUserName(id: Long, newName: String): Response<AuthResponse> {
        val request = UpdateUsernameRequest(newName)
        Log.d("UserRepository", "Updating username for userId=$userId with token=$token")
        return api.updateUsername(id, request)
    }

    suspend fun updatePassword(id: Long, newPassword: String): Response<AuthResponse> {
        val request = UpdatePasswordRequest(newPassword)
        Log.d("UserRepository", "Updating username for userId=$userId with token=$token")
        return api.updatePassword(id, request)
    }
}
