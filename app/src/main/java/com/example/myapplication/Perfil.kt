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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.viewmodel.ProfileState
import com.example.myapplication.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    onBack: () -> Unit,
    onSave: () -> Unit,
    onLogout: () -> Unit,
    onNavigate: (String) -> Unit,
    viewModel: ProfileViewModel = viewModel()
) {
    val state = viewModel.profileState

    Scaffold(
        containerColor = FondoOscuro,
        topBar = {
            TopAppBar(
                title = { Text("Configuración", color = Color.White) },
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state) {
                is ProfileState.Loading -> CircularProgressIndicator(color = VerdeApp)
                is ProfileState.Success -> {
                    val user = state.user
                    Text("Detalles de la Cuenta", color = Color.White, style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    InfoRow(label = "NOMBRE", value = user.name)
                    Spacer(modifier = Modifier.height(16.dp))
                    InfoRow(label = "CORREO", value = user.email)
                    Spacer(modifier = Modifier.height(16.dp))
                    InfoRow(label = "OBJETIVO", value = user.objective)
                    
                    Spacer(modifier = Modifier.height(48.dp))
                    
                    BotonPrincipal(text = "GUARDAR CAMBIOS", onClick = onSave)
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Botón Cerrar Sesión en Rojo
                    Button(
                        onClick = onLogout,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4B4B)),
                        shape = RoundedCornerShape(28.dp)
                    ) {
                        Text("CERRAR SESIÓN", color = Color.White, fontWeight = FontWeight.Bold)
                    }
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

@Composable
fun InfoRow(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(label, color = VerdeApp, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(value, color = Color.White, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(GrisBorde))
    }
}
