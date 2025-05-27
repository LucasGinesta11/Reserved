package com.example.reserved.ui.viewModel.establishment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.reserved.data.repository.EstablishmentRepository

@Suppress("UNCHECKED_CAST")
class EstablishmentViewModelFactory(
    private val repository: EstablishmentRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EstablishmentViewModel::class.java)) {
            return EstablishmentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
