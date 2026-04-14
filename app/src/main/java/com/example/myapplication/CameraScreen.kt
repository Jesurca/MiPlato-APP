package com.example.myapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

/**
 * Pantalla de Cámara.
 * Permite simular el escaneo de alimentos.
 */
@Composable
fun CameraScreen(onBack: () -> Unit) {
    val aquamarine = Color(0xFF7FFFD4)
    val lightBlueBg = Color(0xFFF0F7FF)

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.Home, null) }, label = { Text("INICIO") })
                NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.DateRange, null) }, label = { Text("PLANES") })
                NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.Person, null) }, label = { Text("PERFIL") })
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().background(lightBlueBg).padding(innerPadding).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Cabecera simplificada
            Text(text = "MiPlato", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "Apunta tu cámara a la comida", textAlign = TextAlign.Center, fontSize = 18.sp)

            Spacer(modifier = Modifier.height(32.dp))

            // Visor de Cámara
            Box(
                modifier = Modifier.fillMaxWidth().weight(1f).background(Color.White, RoundedCornerShape(16.dp)).border(2.dp, Color.Black, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(painter = painterResource(id = R.drawable.miplatoimggg), contentDescription = null, modifier = Modifier.size(64.dp), tint = Color.LightGray)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botones inferiores
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botón Volver
                TextButton(onClick = onBack) {
                    Text("Volver al Dashboard", color = Color.Black, fontWeight = FontWeight.Bold)
                }

                // Obturador circular
                Surface(
                    modifier = Modifier.size(70.dp),
                    shape = CircleShape,
                    color = aquamarine,
                    border = BorderStroke(2.dp, Color.Black),
                    onClick = { /* Foto */ }
                ) { }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CameraPreview() {
    MyApplicationTheme {
        CameraScreen(onBack = {})
    }
}
