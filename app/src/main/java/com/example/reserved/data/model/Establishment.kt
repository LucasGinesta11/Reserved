package com.example.reserved.data.model

data class Establishment(
    val id: Long,
    val nombre: String,
    val tipo: String,
    val direccion: String,
    val telefono: Int,
    val descripcion: String,
    val valoracion: Float,
    val imagenUrl: String,
    var isFavorite: Boolean = false
)
