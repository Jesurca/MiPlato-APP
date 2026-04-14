package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                // Navegación simple: login, register, home, camera
                var screen by remember { mutableStateOf("login") }

                Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
                    when (screen) {
                        "login"    -> LoginScreen(onLogin = { screen = "home" }, onGoRegister = { screen = "register" })
                        "register" -> RegisterScreen(onRegistered = { screen = "home" }, onBack = { screen = "login" })
                        "home"     -> HomeScreen(onScan = { screen = "camera" })
                        "camera"   -> CameraScreen(onBack = { screen = "home" })
                    }
                }
            }
        }
    }
}

@Composable
fun LoginScreen(onLogin: () -> Unit, onGoRegister: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LogoPrincipal() // Componente reutilizable
        
        Spacer(modifier = Modifier.height(32.dp))
        Text("Bienvenido", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        
        Spacer(modifier = Modifier.height(32.dp))
        CampoTexto("Email", email, { email = it })
        Spacer(modifier = Modifier.height(16.dp))
        CampoTexto("Contraseña", pass, { pass = it }, isPassword = true)
        
        Spacer(modifier = Modifier.height(48.dp))
        BotonPrincipal("Entrar", onLogin)
        
        Spacer(modifier = Modifier.height(24.dp))
        Text("¿No tienes cuenta? Regístrate", 
            modifier = Modifier.clickable { onGoRegister() },
            color = Color.Gray, fontWeight = FontWeight.Bold)
    }
}
