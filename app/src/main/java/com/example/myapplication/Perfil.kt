package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.viewmodel.ProfileState
import com.example.myapplication.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    onBack: () -> Unit,
    onSave: () -> Unit,
    onNavigate: (String) -> Unit,
    viewModel: ProfileViewModel = viewModel()
) {
    val state = viewModel.profileState

    Scaffold(
        containerColor = FondoOscuro,
        topBar = {
            TopAppBar(
                title = { Text("Mi Perfil", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = FondoOscuro)
            )
        },
        bottomBar = {
            BarraNavegacionComun(currentScreen = "perfil", onNavigate = onNavigate)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state) {
                is ProfileState.Loading -> CircularProgressIndicator(color = VerdeApp)
                is ProfileState.Success -> {
                    val user = state.user
                    Text("Configuración de Perfil", color = Color.White, style = MaterialTheme.typography.headlineMedium)
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Text("Nombre: ${user.name}", color = Color.White)
                    Text("Email: ${user.email}", color = Color.LightGray)
                    Text("Objetivo: ${user.objective}", color = VerdeApp)
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    BotonPrincipal(text = "GUARDAR CAMBIOS", onClick = onSave)
                }
                is ProfileState.Error -> {
                    Text("Error: ${state.message}", color = Color.Red)
                    Button(onClick = { viewModel.fetchUserProfile() }) {
                        Text("Reintentar")
                    }
                }
            }
        }
    }
}
