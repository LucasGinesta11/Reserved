package com.example.reserved.data.remote.api

import com.example.reserved.data.model.Establishment
import com.example.reserved.data.remote.dto.request.FavoriteRequest
import com.example.reserved.data.remote.dto.request.LoginRequest
import com.example.reserved.data.remote.dto.request.RatingRequest
import com.example.reserved.data.remote.dto.request.RegisterRequest
import com.example.reserved.data.remote.dto.request.ReserveRequest
import com.example.reserved.data.remote.dto.request.UpdatePasswordRequest
import com.example.reserved.data.remote.dto.request.UpdateUsernameRequest
import com.example.reserved.data.remote.dto.response.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ReservedApi {

    // Login / Register
    @POST("/reserved/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<AuthResponse>

    @POST("/reserved/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<AuthResponse>


    // Establishments
    @GET("/reserved/establishments")
    suspend fun getEstablishments(): List<Establishment>


    // Favorites
    @GET("/reserved/favorites")
    suspend fun getFavorites(): List<FavoriteRequest>

    @POST("/reserved/favorites")
    suspend fun addFavorite(
        @Body favoriteRequest: FavoriteRequest
    )

    @DELETE("/reserved/favorites")
    suspend fun removeFavorite(
        @Query("userId") userId: Long,
        @Query("establishmentId") establishmentId: Long
    )


    // Reserves
    @GET("reserved/reserves")
    suspend fun getReserves(): List<ReserveRequest>

    @POST("/reserved/reserves")
    suspend fun addReserve(
        @Body reserveRequest: ReserveRequest
    )


    // Users
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


    // Ratings
    @GET("reserved/ratings")
    suspend fun getRatings(): List<RatingRequest>

    @POST("reserved/ratings")
    suspend fun addRating(
        @Body ratingRequest: RatingRequest
    )
}

