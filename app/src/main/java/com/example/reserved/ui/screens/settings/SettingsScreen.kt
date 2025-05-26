package com.example.reserved.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {

    LazyColumn(
        modifier = modifier
    ) {
        // --- CUENTA ---
        item {
            SectionTitle("Cuenta")
        }
        item {
            SettingsOption(
                title = "Cambiar nombre",
                onClick = { navController.navigate("settings/change_name") }
            )
        }
        item {
            SettingsOption(
                title = "Cambiar contraseña",
                onClick = { navController.navigate("settings/change_password") }
            )
        }
        item {
            SettingsOption(
                title = "Cerrar sesión",
                onClick = { navController.navigate("settings/login") }
            )
        }
        item {
            Divider(modifier = Modifier.padding(vertical = 12.dp))
        }

        // --- PREFERENCIAS ---
        item {
            SectionTitle("Preferencias")
        }
        item {
            SettingsOption(
                title = "Idioma",
                subtitle = "Español",
                onClick = { navController.navigate("settings/language") }
            )
        }
        item {
            SettingsOption(
                title = "Tema",
                subtitle = "Automático",
                onClick = { navController.navigate("settings/theme") }
            )
        }
        item {
            SettingsOption(
                title = "Tamaño de letra",
                subtitle = "Medio",
                onClick = { navController.navigate("settings/font_size") }
            )
        }
        item {
            Divider(modifier = Modifier.padding(vertical = 12.dp))
        }

        // --- SEGURIDAD Y PRIVACIDAD ---
        item {
            SectionTitle("Seguridad y privacidad")
        }
        item {
            SettingsOption(
                title = "Autenticación biométrica",
                onClick = { navController.navigate("settings/biometric") }
            )
        }
        item {
            SettingsOption(
                title = "Política de privacidad",
                onClick = { navController.navigate("settings/privacy_policy") }
            )
        }
        item {
            SettingsOption(
                title = "Términos y condiciones",
                onClick = { navController.navigate("settings/terms") }
            )
        }
        item {
            Divider(modifier = Modifier.padding(vertical = 12.dp))
        }

        // --- DATOS Y ALMACENAMIENTO ---
        item {
            SectionTitle("Datos y almacenamiento")
        }
        item {
            SettingsOption(
                title = "Borrar caché",
                onClick = { /* Aquí acción para borrar caché */ }
            )
        }
        item {
            SettingsOption(
                title = "Exportar datos",
                onClick = { /* Acción exportar datos */ }
            )
        }
        item {
            Divider(modifier = Modifier.padding(vertical = 12.dp))
        }

        // --- NOTIFICACIONES ---
        item {
            SectionTitle("Notificaciones")
        }
        item {
            SettingsOption(
                title = "Configurar notificaciones",
                onClick = { navController.navigate("settings/notifications") }
            )
        }
        item {
            Divider(modifier = Modifier.padding(vertical = 12.dp))
        }

        // --- SOPORTE Y AYUDA ---
        item {
            SectionTitle("Soporte y ayuda")
        }
        item {
            SettingsOption(
                title = "Preguntas frecuentes",
                onClick = { navController.navigate("settings/faq") }
            )
        }
        item {
            SettingsOption(
                title = "Contactar soporte",
                onClick = { navController.navigate("settings/contact_support") }
            )
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
fun SettingsOption(
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.bodyLarge)
        if (subtitle != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
