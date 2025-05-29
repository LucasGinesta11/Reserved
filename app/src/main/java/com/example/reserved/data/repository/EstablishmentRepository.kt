package com.example.reserved.data.repository

import com.example.reserved.data.model.Establishment
import com.example.reserved.data.remote.RetrofitInstance
import com.example.reserved.data.remote.dto.request.FavoriteRequest
import com.example.reserved.data.remote.dto.request.RatingRequest
import com.example.reserved.data.remote.dto.request.ReserveRequest

class EstablishmentRepository(token: String?) {
    private val api = RetrofitInstance.getApi()

    // Establishment
    suspend fun getAllEstablishments(): List<Establishment> {
        return api.getEstablishments()
    }


    // Favorites
    suspend fun getFavorites(): List<FavoriteRequest> {
        return api.getFavorites()
    }

    suspend fun addFavorite(userId: Long, establishmentId: Long) {
        api.addFavorite(FavoriteRequest(userId = userId, establishmentId = establishmentId))
    }

    suspend fun removeFavorite(userId: Long, establishmentId: Long) {
        api.removeFavorite(userId, establishmentId)
    }


    // Reservas
    suspend fun getReserves(): List<ReserveRequest>{
        return api.getReserves()
    }

    suspend fun addReserve(userId: Long, establishmentId: Long) {
        api.addReserve(ReserveRequest(userId = userId, establishmentId = establishmentId))
    }


    // Ratings
    suspend fun getRatings(): List<RatingRequest>{
        return api.getRatings()
    }

    suspend fun addRating(userId: Long, establishmentId: Long, rating: Double, comment: String) {
        api.addRating(RatingRequest(userId = userId, establishmentId = establishmentId, rating = rating, comment = comment))
    }
}