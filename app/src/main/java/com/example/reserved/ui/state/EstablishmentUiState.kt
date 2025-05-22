package com.example.reserved.ui.state

import com.example.reserved.data.model.Establishment

sealed class EstablishmentUiState {
    data object Loading : EstablishmentUiState()
    data class Success(val establishments: List<Establishment>): EstablishmentUiState()
    data class Error (val message: String): EstablishmentUiState()
}