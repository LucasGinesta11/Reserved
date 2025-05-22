package com.example.reserved.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.reserved.data.repository.EstablishmentRepository
import com.example.reserved.data.repository.SelectedEstablishment
import com.example.reserved.ui.screens.details.DetailsScreen
import com.example.reserved.ui.screens.favorites.FavoritesScreen
import com.example.reserved.ui.screens.home.EstablishmentScreen
import com.example.reserved.ui.screens.login.LoginScreen
import com.example.reserved.ui.screens.register.RegisterScreen
import com.example.reserved.ui.screens.reserves.ReservesScreen
import com.example.reserved.ui.screens.settings.SettingsScreen
import com.example.reserved.ui.viewModel.EstablishmentViewModel
import com.example.reserved.ui.viewModel.EstablishmentViewModelFactory

@Composable
fun Navigation() {
    val navController = rememberNavController()

    // Aquí definimos un estado para el ViewModel y factory
    // Se crean solo cuando el token esté disponible (después del login)
    // Para simplificar, guardamos el token cuando se navega a home y creamos el VM
    var viewModel: EstablishmentViewModel? = null

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController)
        }

        composable("register") {
            RegisterScreen(navController)
        }

        composable("home/{token}") { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""

            // Aquí creamos factory y VM solo UNA vez para ese token
            val repository = remember(token) { EstablishmentRepository(token) }
            val factory = remember(token) { EstablishmentViewModelFactory(repository) }

            viewModel = viewModel(factory = factory)

            EstablishmentScreen(viewModel = viewModel!!, navController = navController)
        }

        // Otras pantallas que usan el mismo ViewModel
        composable("favorites") {
            if (viewModel != null) {
                FavoritesScreen(viewModel!!, navController)
            } else {
                // Si no hay ViewModel (ej. usuario no logueado), mostrar error o login
                Text("No hay sesión iniciada")
            }
        }

        composable("details") {
            val establishment = SelectedEstablishment.current
            if (establishment != null && viewModel != null) {
                DetailsScreen(establishment = establishment, navController = navController)
            } else {
                Text("Establecimiento no encontrado o sin sesión")
            }
        }

        composable("reserves") {
            ReservesScreen()
        }

        composable("settings") {
            SettingsScreen()
        }
    }
}
