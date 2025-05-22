package com.example.reserved.data.remote

import com.example.reserved.data.model.Establishment
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EstablishmentApi {
    @GET("/reservas/establecimientos")
    suspend fun getEstablishments(): List<Establishment>

    @GET("/reservas/favorites")
    suspend fun getFavorites(): List<FavoriteRequest>

    @POST("favorites")
    suspend fun addFavorite(
        @Body favoriteRequest: FavoriteRequest
    )

    @DELETE("favorites/{id}")
    suspend fun removeFavorite(
        @Path("id") establishmentId: Int
    )

    @POST("/reservas/login")
    suspend fun login(@Body loginRequest: LoginRequest): retrofit2.Response<AuthResponse>

    @POST("/reservas/register")
    suspend fun register(@Body registerRequest: RegisterRequest): retrofit2.Response<AuthResponse>
}

data class LoginRequest(val username: String, val password: String)
data class RegisterRequest(val username: String, val password: String, val email: String, val phone: Int)
data class FavoriteRequest(val establecimientoId: Int)
data class AuthResponse(val token: String, val message: String)
