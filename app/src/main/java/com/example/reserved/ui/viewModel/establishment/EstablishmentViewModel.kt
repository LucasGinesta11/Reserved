package com.example.reserved.ui.viewModel.establishment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reserved.data.repository.EstablishmentRepository
import com.example.reserved.ui.state.EstablishmentUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class EstablishmentViewModel(
    private val repository: EstablishmentRepository
) : ViewModel() {

    private val _state = MutableStateFlow<EstablishmentUiState>(EstablishmentUiState.Loading)
    val state: StateFlow<EstablishmentUiState> = _state

    init {
        getEstablishments()
    }

    private fun getEstablishments() {
        viewModelScope.launch {
            try {
                _state.value = EstablishmentUiState.Loading

                val establishments = repository.getAllEstablishments()

                val favorites = try {
                    repository.getFavorites().map { it.establishment_id.toLong() }
                } catch (e: Exception) {
                    e.printStackTrace()
                    emptyList()
                }

                val result = establishments.map { est ->
                    est.copy(isFavorite = est.id in favorites)
                }

                _state.value = EstablishmentUiState.Success(result)

            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = EstablishmentUiState.Error("Error al cargar los establecimientos.")
            }
        }
    }

    fun toggleFavorite(establishmentId: Long) {
        val currentState = _state.value
        if (currentState is EstablishmentUiState.Success) {
            val updated = currentState.establishments.map {
                if (it.id == establishmentId) it.copy(isFavorite = !it.isFavorite)
                else it
            }
            _state.value = EstablishmentUiState.Success(updated)

            viewModelScope.launch {
                try {
                    val isNowFavorite = updated.first { it.id == establishmentId }.isFavorite
                    if (isNowFavorite) {
                        repository.addFavorite(establishmentId.toInt())
                    } else {
                        repository.removeFavorite(establishmentId.toInt())
                    }
                } catch (e: Exception) {
                    e.message
                }
            }
        }
    }

    fun sortByName() {
        val currentState = _state.value
        if (currentState is EstablishmentUiState.Success) {
            val sorted = currentState.establishments.sortedBy { it.name.lowercase() }
            _state.value = EstablishmentUiState.Success(sorted)
        }
    }

    fun sortByEstablishment() {
        val currentState = _state.value
        if (currentState is EstablishmentUiState.Success) {
            val sorted = currentState.establishments.sortedBy { it.type.lowercase() }
            _state.value = EstablishmentUiState.Success(sorted)
        }
    }

    fun sortByProximity(userLat: Double, userLon: Double) {
        val currentState = _state.value
        if (currentState is EstablishmentUiState.Success) {
            val sorted = currentState.establishments.sortedBy { est ->
                distanceBetween(userLat, userLon, est.latitude, est.longitude)
            }
            _state.value = EstablishmentUiState.Success(sorted)
        }
    }

    private fun distanceBetween(
        lat1: Double, lon1: Double,
        lat2: Double, lon2: Double
    ): Double {
        val R = 6371e3 // radio Tierra en metros
        val phi1 = Math.toRadians(lat1)
        val phi2 = Math.toRadians(lat2)
        val deltaPhi = Math.toRadians(lat2 - lat1)
        val deltaLambda = Math.toRadians(lon2 - lon1)

        val a = sin(deltaPhi / 2) * sin(deltaPhi / 2) +
                cos(phi1) * cos(phi2) *
                sin(deltaLambda / 2) * sin(deltaLambda / 2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        val distance = R * c

        return distance
    }

}