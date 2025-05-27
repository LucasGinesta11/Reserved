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
    var showUsernameDialog by remember { mutableStateOf(false) }
    var newName by remember { mutableStateOf("") }
    val userId = SessionManager.userId

    var showPasswordDialog by remember { mutableStateOf(false) }
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }

    SectionTitle("Cuenta")

    SettingsOption(
        title = "Cambiar nombre",
        onClick = { showUsernameDialog = true }
    )

    SettingsOption(
        title = "Cambiar contraseña",
        onClick = { showPasswordDialog = true }
    )

    SettingsOption(
        title = "Cerrar sesión",
        onClick = { navController.navigate("login") }
    )

    if (showUsernameDialog) {
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
                showUsernameDialog = false
            },
            onDismiss = { showUsernameDialog = false }
        )
    }

    if (showPasswordDialog) {
        ChangePassword(
            currentPassword = currentPassword,
            onCurrentPasswordChange = { currentPassword = it },
            newPassword = newPassword,
            onNewPasswordChange = { newPassword = it },
            onConfirm = {
                userId?.let {
                    userViewModel?.changePassword(it, currentPassword) { success ->
                        Toast.makeText(
                            context,
                            if (success) "Contraseña actualizada" else "Error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                showPasswordDialog = false
                currentPassword = ""
                newPassword = ""
            },
            onDismiss = {
                showPasswordDialog = false
                currentPassword = ""
                newPassword = ""
            }
        )
    }
}
