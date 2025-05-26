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

    private val repository = UserRepository

    fun onLoginClick(onSuccess: (String) -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = repository.login(username.value, password.value)
                if (response.isSuccessful) {
                    val token = response.body()?.token
                    if (!token.isNullOrBlank()) {
                        onSuccess(token)
                    } else {
                        errorMessage.value = "Token vacío o nulo"
                    }
                } else {
                    errorMessage.value = "Error: ${response.message()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "Login fallido: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

}

