    package com.example.reserved.ui.screens.login

    import androidx.compose.foundation.background
    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.Spacer
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.rememberScrollState
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.foundation.verticalScroll
    import androidx.compose.material3.Button
    import androidx.compose.material3.ButtonDefaults
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.OutlinedTextField
    import androidx.compose.material3.Text
    import androidx.compose.material3.TextButton
    import androidx.compose.runtime.Composable
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
    import com.example.reserved.ui.viewModel.LoginViewModel

    @Composable
    fun LoginScreen(navController: NavController, viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
        val username = viewModel.username.value
        val password = viewModel.password.value
        val passwordVisible = viewModel.passwordVisible.value
        val errorMessage = viewModel.errorMessage.value
        val isLoading = viewModel.isLoading.value

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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Bienvenido", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0C59CF), fontFamily = FontFamily.Serif)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Inicia sesión o registrate", fontSize = 16.sp, color = Color.Gray, fontFamily = FontFamily.Serif)
                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = { viewModel.username.value = it },
                    label = { Text("Nombre de usuario", fontFamily = FontFamily.Serif) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    enabled = !isLoading
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { viewModel.password.value = it },
                    label = { Text("Contraseña", fontFamily = FontFamily.Serif) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        TextButton(
                            onClick = { viewModel.passwordVisible.value = !passwordVisible },
                            enabled = !isLoading
                        ) {
                            Text(if (passwordVisible) "Ocultar" else "Mostrar", color = Color(0xFF0C59CF), fontFamily = FontFamily.Serif)
                        }
                    },
                    enabled = !isLoading
                )

                errorMessage?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(it, color = Color.Red, fontFamily = FontFamily.Serif, fontSize = 14.sp)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        viewModel.onLoginClick {
                            navController.navigate("home/${viewModel.authToken.value}") {
                                popUpTo("login") { inclusive = true }
                            }

                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0C59CF), contentColor = Color.White),
                    enabled = !isLoading
                ) {
                    Text(if (isLoading) "Cargando..." else "Iniciar sesión", fontFamily = FontFamily.Serif, fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController.navigate("register") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0C59CF), contentColor = Color.White),
                    enabled = !isLoading
                ) {
                    Text("Registrarse", fontSize = 16.sp, fontFamily = FontFamily.Serif)
                }
            }
        }
    }

