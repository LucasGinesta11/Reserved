package com.example.reserved.data.repository

import com.example.reserved.data.model.Establishment
import com.example.reserved.data.remote.network.RetrofitInstance
import com.example.reserved.data.remote.request.FavoriteRequest

class EstablishmentRepository(token: String?) {
    private val api = RetrofitInstance.getApi(token)

    suspend fun getAllEstablishments(): List<Establishment> {
        return api.getEstablishments()
    }

    suspend fun getFavorites(): List<FavoriteRequest> {
        return api.getFavorites()
    }


    suspend fun addFavorite(establishmentId: Int) {
        api.addFavorite(FavoriteRequest(establishment_id = establishmentId))
    }

    suspend fun removeFavorite(establishmentId: Int) {
        return api.removeFavorite(establishmentId)
    }

}
