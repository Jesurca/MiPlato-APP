package com.example.myapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun HomeScreen(onScanClick: () -> Unit) {
    val aquamarine = Color(0xFF7FFFD4)
    val aliceBlue = Color(0xFFF0F8FF)

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = { /* TODO: Navegar a Inicio */ },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                    label = { Text("INICIO") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black,
                        unselectedIconColor = Color.Gray,
                        selectedTextColor = Color.Black,
                        indicatorColor = aquamarine
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { /* TODO: Navegar a Planes */ },
                    icon = { Icon(Icons.Default.DateRange, contentDescription = "Planes") },
                    label = { Text("PLANES") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { /* TODO: Navegar a Perfil */ },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                    label = { Text("PERFIL") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { /* TODO: Navegar a Ajustes */ },
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Ajustes") },
                    label = { Text("AJUSTES") }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Hola, [Usuario]",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Sección de Calorías
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "KCAL CONSUMIDAS", fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                    Text(text = "1200", fontSize = 32.sp, fontWeight = FontWeight.Bold)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "OBJETIVO", fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                    Text(text = "2000", fontSize = 32.sp, fontWeight = FontWeight.Bold)
                }
            }

            // Barra de progreso de calorías (Simulada)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(Color.White, RoundedCornerShape(10.dp))
                    .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f) // 1200/2000 = 0.6
                        .fillMaxHeight()
                        .background(aliceBlue, RoundedCornerShape(10.dp))
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // --- ESPACIO PARA LA IMAGEN DE LAS BARRAS ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(Color(0xFFFAFAFA), RoundedCornerShape(16.dp))
                    .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                 Image(
                     painter = painterResource(id = R.drawable.miplatoimggg),
                    contentDescription = "Gráfico de macros",
                     modifier = Modifier.fillMaxSize()
                 )
                Text(text = "[ Imagen de Barras / Gráfico ]", color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "RECOMENDACIONES",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Recomendación 1
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.Notifications, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Te faltan 30g de proteína hoy.", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Recomendación 2
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.CheckCircle, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Bebe más agua.", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.weight(1f))

            // Botón Escanear Comida
            Button(
                onClick = { onScanClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = aquamarine),
                shape = RoundedCornerShape(30.dp),
                border = BorderStroke(1.dp, Color.Black)
            ) {
                Text(
                    text = "ESCANEAR COMIDA",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    MyApplicationTheme {
        HomeScreen(onScanClick = {})
    }
}
