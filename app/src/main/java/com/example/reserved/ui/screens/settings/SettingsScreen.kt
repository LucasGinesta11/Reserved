package com.example.reserved.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.reserved.ui.screens.settings.account.AccountSection
import com.example.reserved.ui.viewModel.user.UserViewModel

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    userViewModel: UserViewModel?
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        item {
            AccountSection(navController = navController, userViewModel = userViewModel)
        }
        item {
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
        }

        item {
            PreferencesSection(navController = navController)
        }
        item {
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
        }

        item {
            SecuritySection(navController = navController)
        }
        item {
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
        }

        item {
            NotificationsSection(navController = navController)
        }
        item {
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
        }

        item {
            SupportSection(navController = navController)
        }
    }
}
