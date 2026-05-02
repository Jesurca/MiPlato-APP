package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

/**
 * HomeScreen: Panel de control principal.
 * Muestra el progreso de calorías y acceso al escáner.
 */
@Composable
fun HomeScreen(onScan: () -> Unit, onNavigate: (String) -> Unit) {
    Scaffold(
        bottomBar = { BarraNavegacion(onNavigate) }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp)
        ) {
            // Sección de Bienvenida
            Text("Hola,", fontSize = 16.sp, color = Color.Gray)
            Text("Usuario Pro", fontSize = 26.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(32.dp))

            // Tarjeta central de resumen
            ResumenCaloriasCard()

            Spacer(modifier = Modifier.height(32.dp))

            // Vista de la comida actual
            Text("Tu Comida Actual", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.miplatoimggg),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(180.dp).clip(RoundedCornerShape(24.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.weight(1f))

            // Botón de acción principal
            BotonPrincipal(text = "ESCANEAR COMIDA", onClick = onScan)
        }
    }
}

/**
 * Card que visualiza las calorías consumidas vs el objetivo.
 */
@Composable
fun ResumenCaloriasCard() {
    Card(
        modifier = Modifier.fillMaxWidth().shadow(8.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text("Resumen Diario", color = Color.Gray, fontWeight = FontWeight.Bold)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("1,200", fontSize = 40.sp, fontWeight = FontWeight.Black)
                    Text("kcal consumidas", fontSize = 12.sp, color = Color.Gray)
                }
                Text("Meta: 2,000", modifier = Modifier.align(Alignment.Bottom), fontWeight = FontWeight.Bold)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Barra de progreso
            LinearProgressIndicator(
                progress = { 0.6f },
                modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                color = VerdeApp,
                trackColor = Color(0xFFF0F0F0),
            )
        }
    }
}

/**
 * Barra inferior con iconos de navegación.
 */
@Composable
fun BarraNavegacion(onNavigate: (String) -> Unit) {
    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(
            selected = true, 
            onClick = { onNavigate("home") }, 
            icon = { Icon(Icons.Default.Home, null) }, 
            label = { Text("Inicio") }
        )
        NavigationBarItem(
            selected = false, 
            onClick = { /* onNavigate("planes") */ }, 
            icon = { Icon(Icons.Default.DateRange, null) }, 
            label = { Text("Planes") }
        )
        NavigationBarItem(
            selected = false, 
            onClick = { onNavigate("perfil") }, 
            icon = { Icon(Icons.Default.Person, null) }, 
            label = { Text("Perfil") }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    MyApplicationTheme {
        HomeScreen(onScan = {}, onNavigate = {})
    }
}
