package com.example.reserved.ui.viewModel.user

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reserved.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepository
) : ViewModel() {

    fun changeUsername(userId: Long, newUsername: String, onResult: (Boolean) -> Unit) {
        Log.d("UserViewModel", "Intentando cambiar nombre para userId=$userId a $newUsername")
        viewModelScope.launch {
            try {
                val response = repository.updateUserName(userId, newUsername)
                if (response.isSuccessful) {
                    onResult(true)
                } else {
                    Log.e("UserViewModel", "Error actualizar nombre: ${response.code()} - ${response.errorBody()?.string()}")
                    onResult(false)
                }
            } catch (e: Exception) {
                Log.e("UserViewModel", "Excepción al actualizar nombre", e)
                onResult(false)
            }
        }
    }

    fun changePassword(userId: Long, newPassword: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository.updatePassword(userId, newPassword)
                if (response.isSuccessful) {
                    onResult(true)
                } else {
                    Log.e("UserViewModel", "Error actualizar nombre: ${response.code()} - ${response.errorBody()?.string()}")
                    onResult(false)
                }
            } catch (e: Exception) {
                Log.e("UserViewModel", "Excepción al actualizar nombre", e)
                onResult(false)
            }
        }
    }
}