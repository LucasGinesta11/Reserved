package com.example.reserved.ui.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reserved.data.repository.AuthRepository
import com.example.reserved.data.repository.UserRepository
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    var username = mutableStateOf("")
    var email = mutableStateOf("")
    var password = mutableStateOf("")
    var phone = mutableStateOf("")
    var passwordVisible = mutableStateOf(false)
    var errorMessage = mutableStateOf<String?>(null)
    var successMessage = mutableStateOf<String?>(null)
    var isLoading = mutableStateOf(false)

    private val authRepository = AuthRepository()

    fun onRegisterClick() {
        if (username.value.isBlank() || password.value.isBlank() || email.value.isBlank()) {
            errorMessage.value = "Por favor, rellena todos los campos obligatorios"
            successMessage.value = null
            return
        }

        val phoneInt = phone.value.toIntOrNull()
        if (phone.value.isNotBlank() && phoneInt == null) {
            errorMessage.value = "Teléfono inválido"
            successMessage.value = null
            return
        }

        isLoading.value = true
        errorMessage.value = null
        successMessage.value = null

        viewModelScope.launch {
            try {
                val response = authRepository.register(
                    username = username.value,
                    password = password.value,
                    email = email.value,
                    phone = phoneInt ?: 0
                )
                if (response.isSuccessful) {
                    // Agregar Toast
                    errorMessage.value = null
                } else {
                    errorMessage.value = "Error al registrar: ${response.code()} ${response.message()}"
                    successMessage.value = null
                }
            } catch (e: Exception) {
                errorMessage.value = "Error de conexión: ${e.localizedMessage}"
                successMessage.value = null
            } finally {
                isLoading.value = false
            }
        }
    }
}
