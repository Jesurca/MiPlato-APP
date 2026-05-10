package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.viewmodel.AuthState
import com.example.myapplication.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    onLogin: (String, String) -> Unit, 
    onGoRegister: () -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    
    val authState = authViewModel.authState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoOscuro)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LogoPrincipal()
        Spacer(modifier = Modifier.height(32.dp))
        Text("MiPlato Login", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(32.dp))
        
        CampoTexto("Correo Electrónico", email, { email = it }, icon = Icons.Default.Email)
        Spacer(modifier = Modifier.height(16.dp))
        CampoTexto("Contraseña", pass, { pass = it }, isPassword = true, icon = Icons.Default.Lock)
        
        // LOGIN ERROR MESSAGE
        if (authState is AuthState.Error) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = authState.message,
                color = Color.Red,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(48.dp))
        BotonPrincipal("Iniciar Sesión") { onLogin(email, pass) }
        Spacer(modifier = Modifier.height(24.dp))
        Text("¿Eres nuevo? Regístrate aquí",
            modifier = Modifier.clickable { onGoRegister() },
            color = VerdeApp, fontWeight = FontWeight.Bold)
    }
}
