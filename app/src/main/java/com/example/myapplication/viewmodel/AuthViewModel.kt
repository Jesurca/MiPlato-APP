package com.example.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.AuthRepository
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object SuccessRegistration : AuthState()
    object SuccessLogin : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel(private val repository: AuthRepository = AuthRepository()) : ViewModel() {

    var authState by mutableStateOf<AuthState>(AuthState.Idle)
        private set

    fun register(name: String, email: String, pass: String, objective: String) {
        viewModelScope.launch {
            authState = AuthState.Loading
            val result = repository.registerUser(name, email, pass, objective)
            authState = result.fold(
                onSuccess = { AuthState.SuccessRegistration },
                onFailure = { AuthState.Error(it.message ?: "Error desconocido") }
            )
        }
    }

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            authState = AuthState.Loading
            val result = repository.loginUser(email, pass)
            authState = result.fold(
                onSuccess = { AuthState.SuccessLogin },
                onFailure = { AuthState.Error(it.message ?: "Error de login") }
            )
        }
    }
    
    fun resetState() {
        authState = AuthState.Idle
    }
}
