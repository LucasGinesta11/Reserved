package com.example.reserved.data.session

// Obtener token e id de la sesion
object SessionManager {
    var token: String? = null
    var userId: Long? = null

    fun clear() {
        token = null
        userId = null
    }
}