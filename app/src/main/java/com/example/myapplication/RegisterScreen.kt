package com.example.myapplication

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegisterScreen(onRegistered: (String, String, String, String) -> Unit, onBack: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var confirmPass by remember { mutableStateOf("") }
    var selectedObj by remember { mutableStateOf("Bajar peso") }

    val objectives = listOf("Bajar peso", "Mantenimiento", "Subir masa")
    val context = LocalContext.current

    Scaffold(
        containerColor = FondoOscuro,
        bottomBar = { 
            BarraNavegacionComun(currentScreen = "", onNavigate = {}) 
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier.size(32.dp).clip(RoundedCornerShape(8.dp)).background(VerdeApp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.miplatoimggg),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("MiPlato", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            Text("Crear Cuenta", color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.Bold)
            
            Spacer(modifier = Modifier.height(32.dp))

            // Card Formulario
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = GrisCard),
                border = BorderStroke(1.dp, GrisBorde)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    CampoTexto("Nombre completo", name, { name = it }, icon = Icons.Default.Person)
                    Spacer(modifier = Modifier.height(16.dp))
                    CampoTexto("Correo electrónico", email, { email = it }, icon = Icons.Default.Email)
                    Spacer(modifier = Modifier.height(16.dp))
                    CampoTexto("Contraseña", pass, { pass = it }, isPassword = true, icon = Icons.Default.Lock)
                    Spacer(modifier = Modifier.height(16.dp))
                    CampoTexto("Confirmar contraseña", confirmPass, { confirmPass = it }, isPassword = true, icon = Icons.Default.Refresh)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Card Objetivos
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = GrisCard),
                border = BorderStroke(1.dp, GrisBorde)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("OBJETIVOS", color = VerdeApp, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        objectives.forEach { obj ->
                            val isSelected = selectedObj == obj
                            Surface(
                                modifier = Modifier.weight(1f).height(40.dp).clickable { selectedObj = obj },
                                shape = RoundedCornerShape(20.dp),
                                color = if (isSelected) VerdeApp else Color.Transparent,
                                border = if (!isSelected) BorderStroke(1.dp, GrisBorde) else null
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(obj, color = if (isSelected) Color.Black else Color.White, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            BotonPrincipal(
                text = "Registrarse",
                onClick = { 
                    if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                        Toast.makeText(context, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
                    } else if (pass != confirmPass) {
                        Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                    } else if (pass.length < 8) {
                        Toast.makeText(context, "Mínimo 8 caracteres", Toast.LENGTH_SHORT).show()
                    } else {
                        onRegistered(name, email, pass, selectedObj)
                    }
                }
            )
        }
    }
}
