package com.example.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
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
                onSuccess = { 
                    repository.logout() // Cerramos sesión para forzar el login manual
                    AuthState.SuccessRegistration 
                },
                onFailure = { 
                    AuthState.Error(mapFirebaseError(it)) 
                }
            )
        }
    }

    fun login(email: String, pass: String) {
        if (email.isBlank() || pass.isBlank()) {
            authState = AuthState.Error("Por favor, rellena todos los campos.")
            return
        }
        viewModelScope.launch {
            authState = AuthState.Loading
            val result = repository.loginUser(email, pass)
            authState = result.fold(
                onSuccess = { AuthState.SuccessLogin },
                onFailure = { 
                    AuthState.Error(mapFirebaseError(it)) 
                }
            )
        }
    }
    
    private fun mapFirebaseError(exception: Throwable): String {
        return when (exception) {
            is FirebaseAuthUserCollisionException -> "Este correo ya está registrado."
            is FirebaseAuthInvalidUserException -> "No existe una cuenta con este correo."
            is FirebaseAuthInvalidCredentialsException -> "Correo o contraseña incorrectos."
            else -> exception.localizedMessage ?: "Ocurrió un error inesperado."
        }
    }
    
    fun resetState() {
        authState = AuthState.Idle
    }

    fun setError(message: String) {
        authState = AuthState.Error(message)
    }

    fun logout() {
        repository.logout()
        authState = AuthState.Idle
    }
}
