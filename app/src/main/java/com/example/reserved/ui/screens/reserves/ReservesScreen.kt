package com.example.reserved.ui.screens.reserves

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.reserved.ui.screens.home.EstablishmentCard
import com.example.reserved.ui.state.EstablishmentUiState
import com.example.reserved.ui.viewModel.establishment.EstablishmentViewModel

@Composable
fun ReservesScreen(viewModel: EstablishmentViewModel, modifier: Modifier, navController: NavController) {
    val state by viewModel.state.collectAsState()
    val reserves by viewModel.reserves.collectAsState()

    LazyColumn(modifier = modifier) {
        items(reserves) { reserve ->
            val establishment = (state as? EstablishmentUiState.Success)?.establishments?.firstOrNull {
                it.id == reserve.establishmentId.toLong()
            }

            establishment?.let {
                EstablishmentCard(it, navController, viewModel)
            }
        }
    }
}