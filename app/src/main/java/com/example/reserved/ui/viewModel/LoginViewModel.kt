package com.example.reserved.ui.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reserved.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    var username = mutableStateOf("")
    var password = mutableStateOf("")
    var passwordVisible = mutableStateOf(false)
    var errorMessage = mutableStateOf<String?>(null)
    var isLoading = mutableStateOf(false)

    val authToken = mutableStateOf<String?>(null)

    fun onLoginClick(onSuccess: () -> Unit) {
        if (username.value.isBlank() || password.value.isBlank()) {
            errorMessage.value = "Rellena todos los campos"
            return
        }

        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = UserRepository.login(username.value, password.value)
                if (response.isSuccessful) {
                    val body = response.body()
                    authToken.value = body?.token
                    println("TOKEN RECIBIDO: ${body?.token}")
                    onSuccess()
                } else {
                    errorMessage.value = "Error de autenticación (${response.code()})"
                }
            } catch (e: Exception) {
                errorMessage.value = "Error de red: ${e.localizedMessage}"
            } finally {
                isLoading.value = false
            }
        }
    }
}

