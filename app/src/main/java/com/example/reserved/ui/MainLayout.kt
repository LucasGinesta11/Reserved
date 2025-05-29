package com.example.reserved.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.launch
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.reserved.data.remote.RetrofitInstance
import com.example.reserved.data.repository.AuthRepository
import com.example.reserved.data.session.SessionManager
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout(
    navController: NavHostController,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    title: String,
    onSortByName: () -> Unit = {},
    onSortByEstablishment: () -> Unit = {},
    onSortByProximity: () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    val scope = rememberCoroutineScope()
    val currentBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = currentBackStackEntry?.destination?.route ?: ""
    val tokenState = rememberSaveable { mutableStateOf(SessionManager.token) }

    if (currentRoute == "login" || currentRoute == "register") {
        content(PaddingValues())
        return
    }

    var filterMenuExpanded by remember { mutableStateOf(false) }

    fun logout() {
        SessionManager.clear()
        RetrofitInstance.reset()

        // Navegación: limpiar todo el backstack
        navController.navigate("login") {
            popUpTo(0) { inclusive = true } // Mata todo el grafo de navegación
            launchSingleTop = true
        }
    }




    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    "Opciones",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )

                val items = listOf(
                    "Establecimientos" to "home",
                    "Reservas" to "reserves",
                    "Favoritos" to "favorites",
                    "Ajustes" to "settings",
                    "Cerrar sesión" to "logout"
                )

                items.forEach { (label, route) ->
                    NavigationDrawerItem(
                        label = { Text(label) },
                        selected = currentRoute == route,
                        onClick = {
                            if (route == "logout") {
                                logout() // ✅ ahora sin parámetros
                            } else {
                                navController.navigate(route) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }

                    )
                }

            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(title, color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = "Menú",
                                tint = Color.White
                            )
                        }
                    },
                    actions = {
                        if (currentRoute == "home") {
                            IconButton(onClick = { filterMenuExpanded = true }) {
                                Icon(
                                    imageVector = Icons.Default.FilterList,
                                    contentDescription = "Filtro",
                                    tint = Color.White
                                )
                            }
                            DropdownMenu(
                                expanded = filterMenuExpanded,
                                onDismissRequest = { filterMenuExpanded = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Ordenar por nombre") },
                                    onClick = {
                                        filterMenuExpanded = false
                                        onSortByName()
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Ordenar por establecimiento") },
                                    onClick = {
                                        filterMenuExpanded = false
                                        onSortByEstablishment()
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Ordenar por cercanía") },
                                    onClick = {
                                        filterMenuExpanded = false
                                        onSortByProximity()
                                    }
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Blue
                    )
                )
            }
        ) { padding ->
            content(padding)
        }
    }
}


