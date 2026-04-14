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

@Composable
fun CameraScreen(onBackToDashboard: () -> Unit) {
    val aquamarine = Color(0xFF7FFFD4)
    val lightBlueBg = Color(0xFFF0F7FF)

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.Home, null) }, label = { Text("INICIO") })
                NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.DateRange, null) }, label = { Text("PLANES") })
                NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.Person, null) }, label = { Text("PERFIL") })
                NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.Settings, null) }, label = { Text("AJUSTES") })
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(lightBlueBg)
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header: Logo y Nombre
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Surface(
                    modifier = Modifier.size(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color.Gray),
                    color = Color.White
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.miplatoimggg),
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp),
                        tint = Color.Unspecified
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "MiPlato",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
            }

            Text(
                text = "Apunta tu cámara\na la comida",
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 32.dp),
                color = Color.DarkGray
            )

            // Visor de la Cámara
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .border(2.dp, Color.Black, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.miplatoimggg),
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = Color.LightGray
                    )
                    Text(text = "CÁMARA", color = Color.Gray, fontWeight = FontWeight.Bold)
                }
            }

            // Controles Inferiores
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                // Botón Volver al Dashboard
                OutlinedButton(
                    onClick = { onBackToDashboard() },
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .width(100.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color.Black),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    Text(
                        text = "Volver al\nDashboard",
                        fontSize = 12.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        lineHeight = 14.sp
                    )
                }

                // Botón Obturador Circular
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("TOMAR FOTO", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("ESCANEAR", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Surface(
                        modifier = Modifier.size(80.dp),
                        shape = CircleShape,
                        color = Color(0xFF7FFFD4), // Aquamarine
                        border = BorderStroke(2.dp, Color.Black),
                        onClick = { /* Acción de tomar foto */ }
                    ) {}
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CameraPreview() {
    MyApplicationTheme {
        CameraScreen(onBackToDashboard = {})
    }
}
