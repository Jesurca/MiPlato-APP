package com.example.myapplication

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

@Composable
fun LoginScreen(onLogin: (String, String) -> Unit, onGoRegister: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LogoPrincipal()
        Spacer(modifier = Modifier.height(32.dp))
        Text("MiPlato Login", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(32.dp))
        CampoTexto("Gmail", email, { email = it })
        Spacer(modifier = Modifier.height(16.dp))
        CampoTexto("Contraseña", pass, { pass = it }, isPassword = true)
        Spacer(modifier = Modifier.height(48.dp))
        BotonPrincipal("Iniciar Sesión") { onLogin(email, pass) }
        Spacer(modifier = Modifier.height(24.dp))
        Text("¿Eres nuevo? Regístrate aquí",
            modifier = Modifier.clickable { onGoRegister() },
            color = VerdeApp, fontWeight = FontWeight.Bold)
    }
}
