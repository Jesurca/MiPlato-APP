package com.example.myapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Pantalla de Registro conectada con Firebase Auth.
 */
@Composable
fun RegisterScreen(onRegistered: (String, String) -> Unit, onBack: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var selectedObj by remember { mutableStateOf("") }

    val objectives = listOf("Bajar peso", "Mantener", "Subir masa")

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogoPrincipal(size = 80)
        
        Spacer(modifier = Modifier.height(24.dp))
        Text("Crear Cuenta", fontSize = 28.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(24.dp))
        CampoTexto("Nombre completo", name, { name = it })
        Spacer(modifier = Modifier.height(8.dp))
        CampoTexto("Correo electrónico (Gmail)", email, { email = it })
        Spacer(modifier = Modifier.height(8.dp))
        CampoTexto("Contraseña", pass, { pass = it }, isPassword = true)

        Spacer(modifier = Modifier.height(24.dp))
        Text("Tu objetivo:", modifier = Modifier.fillMaxWidth(), fontWeight = FontWeight.SemiBold)
        
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            objectives.forEach { obj ->
                val isSelected = selectedObj == obj
                Surface(
                    modifier = Modifier.weight(1f).clickable { selectedObj = obj },
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) VerdeApp else Color(0xFFF7F9FB),
                    border = BorderStroke(1.dp, if (isSelected) Color.Black else Color.Transparent)
                ) {
                    Text(obj, modifier = Modifier.padding(10.dp), textAlign = TextAlign.Center, fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        // Enviamos los datos a la lógica de Firebase en MainActivity
        BotonPrincipal("Registrarse") { onRegistered(email, pass) }

        Spacer(modifier = Modifier.weight(1f))
        Text("¿Ya tienes cuenta? Inicia sesión", 
            modifier = Modifier.clickable { onBack() },
            color = Color.Gray, fontWeight = FontWeight.Bold)
    }
}
