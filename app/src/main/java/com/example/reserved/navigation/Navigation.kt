package com.example.reserved.navigation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.reserved.data.SessionManager
import com.example.reserved.data.remote.LocationUtils
import com.example.reserved.data.repository.EstablishmentRepository
import com.example.reserved.data.repository.SelectedEstablishment
import com.example.reserved.ui.MainLayout
import com.example.reserved.ui.screens.details.DetailsScreen
import com.example.reserved.ui.screens.favorites.FavoritesScreen
import com.example.reserved.ui.screens.home.EstablishmentScreen
import com.example.reserved.ui.screens.login.LoginScreen
import com.example.reserved.ui.screens.register.RegisterScreen
import com.example.reserved.ui.screens.reserves.ReservesScreen
import com.example.reserved.ui.screens.settings.SettingsScreen
import com.example.reserved.ui.viewModel.EstablishmentViewModel
import com.example.reserved.ui.viewModel.EstablishmentViewModelFactory
import com.example.reserved.ui.viewModel.LoginViewModel
import android.Manifest
import android.util.Log
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Navigation() {
    val navController = rememberNavController()

    val tokenState = rememberSaveable { mutableStateOf(SessionManager.token) }

    val viewModel: EstablishmentViewModel? = if (!tokenState.value.isNullOrEmpty()) {
        val repository = remember(tokenState.value) { EstablishmentRepository(tokenState.value!!) }
        val factory = remember(tokenState.value) { EstablishmentViewModelFactory(repository) }
        viewModel(factory = factory)
    } else null

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            val loginViewModel: LoginViewModel = viewModel()
            LoginScreen(navController, onLoginSuccess = { token ->
                SessionManager.token = token
                tokenState.value = token
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            }, viewModel = loginViewModel)
        }

        composable("register") {
            RegisterScreen(navController)
        }

        composable("home") {
            val context = LocalContext.current
            val coroutineScope = rememberCoroutineScope()

            val locationPermissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
                onResult = { isGranted ->
                    if (isGranted && viewModel != null) {
                        coroutineScope.launch {
                            LocationUtils.init(context)
                            val location = LocationUtils.getCurrentLocation()
                            if (location == null) {
                                Log.d("Navigation", "Ubicación es null, no se ordena")
                            } else {
                                Log.d(
                                    "Navigation",
                                    "Ubicación obtenida: ${location.latitude}, ${location.longitude}"
                                )
                            }

                            location?.let {
                                viewModel.sortByProximity(it.latitude, it.longitude)
                            }
                        }
                    }
                }
            )

            if (viewModel != null) {
                MainLayout(
                    navController = navController,
                    title = "Establecimientos",
                    onSortByName = { viewModel.sortByName() },
                    onSortByEstablishment = { viewModel.sortByEstablishment() },
                    onSortByProximity = {
                        // Lanzamos la petición de permisos cuando el usuario pulse el botón
                        locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    }
                ) { padding ->
                    EstablishmentScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(padding),
                        navController = navController
                    )
                }
            } else {
                LaunchedEffect(Unit) {
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            }
        }


        composable("favorites") {
            if (viewModel != null) {
                MainLayout(navController, title = "Favoritos") { padding ->
                    FavoritesScreen(viewModel, Modifier.padding(padding), navController)
                }
            }
        }

        composable("details") {
            MainLayout(navController, title = "Detalles") { padding ->
                val establishment = SelectedEstablishment.current
                if (establishment != null) {
                    DetailsScreen(establishment, Modifier.padding(padding), navController)
                }
            }
        }

        composable("reserves") {
            MainLayout(navController, title = "Reservas") { padding ->
                ReservesScreen(Modifier.padding(padding), navController)
            }
        }

        composable("settings") {
            MainLayout(navController, title = "Ajustes") { padding ->
                SettingsScreen(Modifier.padding(padding), navController)
            }
        }
    }
}
