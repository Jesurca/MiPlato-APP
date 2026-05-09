package com.example.myapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Pantalla de Registro con el nuevo diseño visual oscuro.
 */
@Composable
fun RegisterScreen(onRegistered: (String, String) -> Unit, onBack: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var confirmPass by remember { mutableStateOf("") }
    var selectedObj by remember { mutableStateOf("Bajar peso") }

    val objectives = listOf("Bajar peso", "Mantener", "Subir masa")

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
            // Top Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(VerdeApp),
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
            
            Text(
                "Crear Cuenta", 
                color = Color.White, 
                fontSize = 36.sp, 
                fontWeight = FontWeight.Bold, 
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                "Únete a la ciencia de la nutrición inteligente.", 
                color = TextoGris, 
                fontSize = 16.sp, 
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Info Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = GrisCard),
                border = BorderStroke(1.dp, GrisBorde)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("INFORMACIÓN DEL USUARIO", color = VerdeApp, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    CampoTexto("Nombre completo", name, { name = it }, icon = Icons.Default.Person)
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    CampoTexto("Correo electrónico", email, { email = it }, icon = Icons.Default.Email)
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    CampoTexto(
                        "Contraseña", 
                        pass, 
                        { pass = it }, 
                        isPassword = true, 
                        icon = Icons.Default.Lock,
                        trailingIcon = { Icon(Icons.Default.VisibilityOff, null, tint = TextoGris) }
                    )
                    Text("[Min. 8 caracteres]", color = TextoGris, fontSize = 10.sp, modifier = Modifier.padding(top = 4.dp))
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    CampoTexto("Confirmar contraseña", confirmPass, { confirmPass = it }, isPassword = true, icon = Icons.Default.Refresh)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Objectives Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = GrisCard),
                border = BorderStroke(1.dp, GrisBorde)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row {
                        Text("OBJETIVOS INICIALES ", color = VerdeApp, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Text("(opcional)", color = TextoGris, fontSize = 11.sp)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        objectives.forEach { obj ->
                            val isSelected = selectedObj == obj
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(36.dp)
                                    .clickable { selectedObj = obj },
                                shape = RoundedCornerShape(18.dp),
                                color = if (isSelected) VerdeApp else GrisBorde,
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        obj, 
                                        color = if (isSelected) Color.Black else Color.White, 
                                        fontSize = 12.sp, 
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            BotonPrincipal(
                text = "Registrarse",
                onClick = { onRegistered(email, pass) }
            )

            Spacer(modifier = Modifier.height(24.dp))
            
            Text("O regístrate con", color = TextoGris, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                SocialIconPlaceholder("Google")
                SocialIconPlaceholder("iOS")
            }

            Spacer(modifier = Modifier.height(32.dp))
            
            Row {
                Text("¿Ya tienes cuenta? ", color = Color.White)
                Text(
                    "Iniciar sesión", 
                    color = VerdeApp, 
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onBack() }
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun SocialIconPlaceholder(name: String) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .background(GrisCard, RoundedCornerShape(12.dp))
            .border(BorderStroke(1.dp, GrisBorde), RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.size(20.dp).background(Color.White.copy(alpha = 0.1f)))
    }
}
