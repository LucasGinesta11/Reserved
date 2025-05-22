package com.example.reserved.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reserved.data.model.Establishment
import com.example.reserved.data.repository.EstablishmentRepository
import com.example.reserved.ui.state.EstablishmentUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
                    repository.getFavorites().map { it.establecimientoId.toLong() }
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
                    // Opcional: revertir el cambio o mostrar error
                }
            }
        }
    }


}