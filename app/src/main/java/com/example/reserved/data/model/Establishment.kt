package com.example.reserved.data.model

data class Establishment(
    val id: Long,
    val name: String,
    val type: String,
    val address: String,
    val phone: Int,
    val description: String,
    val rating: Double,
    val image: String,
    var isFavorite: Boolean = false,
    val longitude: Double,
    val latitude: Double
)
