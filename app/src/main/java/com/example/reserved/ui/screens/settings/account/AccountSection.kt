package com.example.reserved.ui.screens.settings.account

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.reserved.data.session.SessionManager
import com.example.reserved.ui.screens.settings.SectionTitle
import com.example.reserved.ui.screens.settings.SettingsOption
import com.example.reserved.ui.viewModel.user.UserViewModel

@Composable
fun AccountSection(
    navController: NavController,
    userViewModel: UserViewModel?
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var newName by remember { mutableStateOf("") }
    val userId = SessionManager.userId

    SectionTitle("Cuenta")

    SettingsOption(
        title = "Cambiar nombre",
        onClick = { showDialog = true }
    )

    SettingsOption(
        title = "Cambiar contraseña",
        onClick = { navController.navigate("settings/change-password") }
    )

    SettingsOption(
        title = "Cerrar sesión",
        onClick = { navController.navigate("login") }
    )

    if (showDialog) {
        ChangeUsername(
            newName = newName,
            onNewNameChange = { newName = it },
            onConfirm = {
                userId?.let {
                    userViewModel?.changeUsername(it, newName) { success ->
                        Toast.makeText(
                            context,
                            if (success) "Nombre actualizado" else "Error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }
}
