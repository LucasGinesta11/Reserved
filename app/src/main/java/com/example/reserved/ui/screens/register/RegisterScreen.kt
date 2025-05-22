package com.example.reserved.ui.screens.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.reserved.ui.viewModel.RegisterViewModel

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val username by viewModel.username
    val email by viewModel.email
    val password by viewModel.password
    val phone by viewModel.phone
    val passwordVisible by viewModel.passwordVisible
    val errorMessage by viewModel.errorMessage
    val successMessage by viewModel.successMessage
    val isLoading by viewModel.isLoading

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                "Crear cuenta",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0C59CF),
                fontFamily = FontFamily.Serif
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = username,
                onValueChange = {
                    viewModel.username.value = it
                    viewModel.errorMessage.value = null
                    viewModel.successMessage.value = null
                },
                label = { Text("Nombre de usuario", fontFamily = FontFamily.Serif) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    viewModel.email.value = it
                    viewModel.errorMessage.value = null
                    viewModel.successMessage.value = null
                },
                label = { Text("Email", fontFamily = FontFamily.Serif) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                enabled = !isLoading,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {
                    viewModel.password.value = it
                    viewModel.errorMessage.value = null
                    viewModel.successMessage.value = null
                },
                label = { Text("Contraseña", fontFamily = FontFamily.Serif) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    TextButton(onClick = { viewModel.passwordVisible.value = !passwordVisible }) {
                        Text(
                            if (passwordVisible) "Ocultar" else "Mostrar",
                            color = Color(0xFF0C59CF),
                            fontFamily = FontFamily.Serif
                        )
                    }
                },
                enabled = !isLoading,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = {
                    viewModel.phone.value = it
                    viewModel.errorMessage.value = null
                    viewModel.successMessage.value = null
                },
                label = { Text("Teléfono", fontFamily = FontFamily.Serif) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                enabled = !isLoading,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            errorMessage?.let {
                Text(
                    it,
                    color = Color.Red,
                    fontFamily = FontFamily.Serif,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            successMessage?.let {
                Text(
                    it,
                    color = Color(0xFF0C8F1E),
                    fontFamily = FontFamily.Serif,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.onRegisterClick() },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0C59CF),
                    contentColor = Color.White
                ),
                enabled = !isLoading
            ) {
                Text(
                    text = if (isLoading) "Registrando..." else "Guardar",
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Serif
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { navController.navigate("login") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0C59CF),
                    contentColor = Color.White
                ),
                enabled = !isLoading
            ) {
                Text(
                    text = "Cancelar",
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Serif
                )
            }
        }
    }
}
