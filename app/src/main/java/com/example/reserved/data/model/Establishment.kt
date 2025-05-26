package com.example.reserved.data.model

data class Establishment(
    val id: Long,
    val nombre: String,
    val tipo: String,
    val direccion: String,
    val telefono: Int,
    val descripcion: String,
    val rating: Double,
    val imagenUrl: String,
    var isFavorite: Boolean = false,
    val length: Double,
    val latitude: Double
)
