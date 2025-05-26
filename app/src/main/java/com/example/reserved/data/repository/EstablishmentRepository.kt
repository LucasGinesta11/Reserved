package com.example.reserved.data.repository

import com.example.reserved.data.model.Establishment
import com.example.reserved.data.remote.FavoriteRequest
import com.example.reserved.data.remote.RetrofitInstance

class EstablishmentRepository(token: String?) {
    private val api = RetrofitInstance.getApi(token)

    suspend fun getAllEstablishments(): List<Establishment> {
        return api.getEstablishments()
    }

    suspend fun getFavorites(): List<FavoriteRequest> {
        return api.getFavorites()
    }


    suspend fun addFavorite(establishmentId: Int) {
        println("Añadiendo favorito: $establishmentId")
        return api.addFavorite(FavoriteRequest(establishmentId))
    }

    suspend fun removeFavorite(establishmentId: Int) {
        return api.removeFavorite(establishmentId)
    }

}
