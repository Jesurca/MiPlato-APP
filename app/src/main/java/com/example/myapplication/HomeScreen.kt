package com.example.myapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun HomeScreen(onScan: () -> Unit, onNavigate: (String) -> Unit) {
    Scaffold(
        containerColor = FondoOscuro,
        bottomBar = { BarraNavegacionComun("home", onNavigate) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.Gray) // Espacio para imagen de perfil
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("MiPlato", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
                Icon(Icons.Default.NotificationsNone, contentDescription = null, tint = VerdeApp)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text("Hola, Juan", color = VerdeApp, fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Text("Tu rendimiento de hoy está en camino.", color = TextoGris, fontSize = 14.sp)

            Spacer(modifier = Modifier.height(24.dp))

            // Calorie Card
            CalorieDonutCard()

            Spacer(modifier = Modifier.height(24.dp))

            // Macro Bars
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MacroVerticalBar("PROTEÍNAS", "85g / 130g", 0.65f, Modifier.weight(1f))
                MacroVerticalBar("CARBOS", "110g / 250g", 0.44f, Modifier.weight(1f))
                MacroVerticalBar("GRASAS", "45g / 70g", 0.64f, Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text("Recomendaciones", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            RecomendacionItem(
                icon = Icons.Outlined.AccessTime,
                text = "Te faltan 30g de proteína hoy.",
                color = VerdeApp
            )
            Spacer(modifier = Modifier.height(12.dp))
            RecomendacionItem(
                icon = Icons.Outlined.WaterDrop,
                text = "Bebe más agua. (1.5L / 3L)",
                color = Color(0xFFFFD150)
            )

            Spacer(modifier = Modifier.height(32.dp))

            BotonPrincipal(
                text = "ESCANEAR COMIDA", 
                onClick = onScan,
                icon = Icons.Default.PhotoCamera
            )
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun CalorieDonutCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = GrisCard),
        border = BorderStroke(1.dp, GrisBorde)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                progress = { 0.6f },
                modifier = Modifier.size(200.dp),
                color = VerdeApp,
                strokeWidth = 12.dp,
                trackColor = GrisBorde,
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("1200", color = Color.White, fontSize = 48.sp, fontWeight = FontWeight.Black)
                Text("DE 2000 KCAL", color = TextoGris, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun MacroVerticalBar(label: String, value: String, progress: Float, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(200.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = GrisCard),
        border = BorderStroke(1.dp, GrisBorde)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label, color = VerdeApp, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            
            // Barra vertical
            Box(
                modifier = Modifier
                    .width(12.dp)
                    .fillMaxHeight(0.7f)
                    .clip(CircleShape)
                    .background(GrisBorde),
                contentAlignment = Alignment.BottomCenter
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(progress)
                        .background(VerdeApp)
                )
            }
            
            Text(value, color = Color.White, fontSize = 10.sp, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        }
    }
}

@Composable
fun RecomendacionItem(icon: ImageVector, text: String, color: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = GrisCard),
        border = BorderStroke(1.dp, GrisBorde)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = color)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = text, color = Color.White, fontSize = 14.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    MyApplicationTheme {
        HomeScreen(onScan = {}, onNavigate = {})
    }
}
