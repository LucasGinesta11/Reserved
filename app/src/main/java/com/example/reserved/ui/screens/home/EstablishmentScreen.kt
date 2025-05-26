package com.example.reserved.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.reserved.data.model.Establishment
import com.example.reserved.data.repository.SelectedEstablishment
import com.example.reserved.ui.state.EstablishmentUiState
import com.example.reserved.ui.viewModel.EstablishmentViewModel

@Composable
fun EstablishmentScreen(viewModel: EstablishmentViewModel, modifier: Modifier, navController: NavController) {

    val state by viewModel.state.collectAsState()

    Box(modifier = modifier) {
        when (state) {
            is EstablishmentUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is EstablishmentUiState.Success -> {
                val establishments = (state as EstablishmentUiState.Success).establishments
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    items(establishments) { establishment ->
                        EstablishmentCard(
                            establishment = establishment,
                            navController = navController,
                            viewModel = viewModel
                        )
                    }

                }
            }

            is EstablishmentUiState.Error -> {
                val message = (state as EstablishmentUiState.Error).message
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}


@Composable
fun EstablishmentCard(
    establishment: Establishment,
    navController: NavController,
    viewModel: EstablishmentViewModel
) {
    Card(
        modifier = Modifier
            .width(400.dp)
            .height(300.dp),
        onClick = {
            SelectedEstablishment.current = establishment
            navController.navigate("details")
        },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = establishment.nombre, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = establishment.direccion, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = rememberAsyncImagePainter(establishment.imagenUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Icon(
                imageVector = if (establishment.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = if (establishment.isFavorite) "Favorito" else "No favorito",
                tint = if (establishment.isFavorite) Color.Red else Color.Gray,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .clickable {
                        viewModel.toggleFavorite(establishment.id)
                    }
            )
        }
    }
}
