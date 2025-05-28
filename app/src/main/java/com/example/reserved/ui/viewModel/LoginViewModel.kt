package com.example.reserved.ui.viewModel

import android.util.Base64
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reserved.data.repository.AuthRepository
import com.example.reserved.data.repository.AccountRepository
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
    private var accountRepository: AccountRepository? = null

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
                        }

                        accountRepository = AccountRepository(user.token)

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
            val parts = token.split(".")
            if (parts.size < 2) return null

            val payload = parts[1]

            val decodedBytes = Base64.decode(payload, Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP)
            val decodedPayload = String(decodedBytes)

            val jsonObject = JSONObject(decodedPayload)

            return jsonObject.optInt("user_id", -1).takeIf { it != -1 }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}
