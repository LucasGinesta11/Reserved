package com.example.reserved.data.remote.dto.request

data class RatingRequest (
    val userId: Long,
    val establishmentId: Long,
    val rating: Double,
    val comment: String
)