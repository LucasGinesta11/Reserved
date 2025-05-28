package com.example.reserved.data.remote.dto.request

data class RegisterRequest(
    val username: String,
    val password: String,
    val email: String,
    val phone: String
)
