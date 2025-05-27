package com.example.reserved.data.remote.api

import com.example.reserved.data.model.Establishment
import com.example.reserved.data.remote.request.FavoriteRequest
import com.example.reserved.data.remote.request.LoginRequest
import com.example.reserved.data.remote.request.RegisterRequest
import com.example.reserved.data.remote.request.UpdatePasswordRequest
import com.example.reserved.data.remote.request.UpdateUsernameRequest
import com.example.reserved.data.remote.response.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface EstablishmentApi {
    @GET("/reserved/establishments")
    suspend fun getEstablishments(): List<Establishment>


    @GET("/reserved/favorites")
    suspend fun getFavorites(): List<FavoriteRequest>

    @POST("/reserved/favorites")
    suspend fun addFavorite(
        @Body favoriteRequest: FavoriteRequest
    )

    @DELETE("/reserved/favorites/{id}")
    suspend fun removeFavorite(
        @Path("id") establishment_id: Int
    )

    @POST("/reserved/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<AuthResponse>

    @POST("/reserved/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<AuthResponse>

    @PATCH("reserved/users/{id}")
    suspend fun updateUsername(
        @Path("id") id: Long,
        @Body request: UpdateUsernameRequest
    ): Response<AuthResponse>

    @PATCH("reserved/users/{id}")
    suspend fun updatePassword(
        @Path("id") id: Long,
        @Body request: UpdatePasswordRequest
    ): Response<AuthResponse>

}

