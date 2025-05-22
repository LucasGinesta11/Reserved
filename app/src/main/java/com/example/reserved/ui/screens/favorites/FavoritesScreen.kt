package com.example.reserved.ui.screens.favorites

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.example.reserved.ui.screens.home.EstablishmentCard
import com.example.reserved.ui.state.EstablishmentUiState
import com.example.reserved.ui.viewModel.EstablishmentViewModel

@Composable
fun FavoritesScreen(viewModel: EstablishmentViewModel, navController: NavController) {
    val state by viewModel.state.collectAsState()

    when (state) {
        is EstablishmentUiState.Success -> {
            val favorites = (state as EstablishmentUiState.Success).establishments.filter { it.isFavorite }
            LazyColumn {
                items(favorites) { establishment ->
                    EstablishmentCard(establishment, navController, viewModel)
                }
            }
        }

        is EstablishmentUiState.Loading -> {
            CircularProgressIndicator()
        }

        is EstablishmentUiState.Error -> {
            Text("Error al cargar favoritos")
        }
    }
}
