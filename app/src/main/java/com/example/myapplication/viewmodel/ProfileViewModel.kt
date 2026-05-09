package com.example.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.User
import com.example.myapplication.repository.AuthRepository
import kotlinx.coroutines.launch

sealed class ProfileState {
    object Loading : ProfileState()
    data class Success(val user: User) : ProfileState()
    data class Error(val message: String) : ProfileState()
}

class ProfileViewModel(private val repository: AuthRepository = AuthRepository()) : ViewModel() {

    var profileState by mutableStateOf<ProfileState>(ProfileState.Loading)
        private set

    init {
        fetchUserProfile()
    }

    fun fetchUserProfile() {
        viewModelScope.launch {
            profileState = ProfileState.Loading
            val user = repository.getUserData()
            profileState = if (user != null) {
                ProfileState.Success(user)
            } else {
                ProfileState.Error("No se pudo cargar el perfil")
            }
        }
    }
}
