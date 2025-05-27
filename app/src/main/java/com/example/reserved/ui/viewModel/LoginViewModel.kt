package com.example.reserved.ui.viewModel

import android.util.Base64
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reserved.data.repository.AuthRepository
import com.example.reserved.data.repository.UserRepository
import com.example.reserved.data.session.SessionManager
import kotlinx.coroutines.launch
import org.json.JSONObject

class LoginViewModel : ViewModel() {

    var username = mutableStateOf("")
    var password = mutableStateOf("")
    var passwordVisible = mutableStateOf(false)
    var errorMessage = mutableStateOf<String?>(null)
    var isLoading = mutableStateOf(false)

    private val authRepository = AuthRepository()
    private var userRepository: UserRepository? = null

    fun onLoginClick(onSuccess: (String) -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = authRepository.login(username.value, password.value)
                if (response.isSuccessful) {
                    val user = response.body()
                    Log.d("LoginViewModel", "Login response: $user")

                    if (user != null && user.token.isNotBlank()) {
                        SessionManager.token = user.token
                        val userId = getUserIdFromToken(user.token)
                        if (userId != null) {
                            SessionManager.userId = userId.toLong()
                        } else {
                            // Manejar error: no se pudo extraer userId del token
                        }

                        userRepository = UserRepository(user.token) // Aquí creas repo con token

                        onSuccess(user.token)
                    } else {
                        errorMessage.value = "Error en datos de usuario"
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

    fun getUserIdFromToken(token: String): Int? {
        try {
            // El token tiene 3 partes separadas por '.': header.payload.signature
            val parts = token.split(".")
            if (parts.size < 2) return null

            val payload = parts[1]

            // Decodificar base64 URL-safe (sin relleno '=')
            val decodedBytes = Base64.decode(payload, Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP)
            val decodedPayload = String(decodedBytes)

            // Parsear JSON para obtener userId (puede variar el nombre del campo, por ejemplo "user_id" o "usuario_id")
            val jsonObject = JSONObject(decodedPayload)

            // Cambia "user_id" por el nombre real que tienes en tu token
            return jsonObject.optInt("user_id", -1).takeIf { it != -1 }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}
