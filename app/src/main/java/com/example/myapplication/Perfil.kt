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
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingFlat
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun PerfilScreen(onBack: () -> Unit, onSave: () -> Unit, onNavigate: (String) -> Unit) {
    var objetivo by remember { mutableStateOf("Déficit calórico") }

    Scaffold(
        containerColor = FondoOscuro,
        bottomBar = { BarraNavegacionComun("perfil", onNavigate) }
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
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(VerdeApp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.PhotoCamera, contentDescription = null, modifier = Modifier.size(18.dp), tint = Color.Black)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("MiPlato", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
                Icon(Icons.Default.NotificationsNone, contentDescription = null, tint = Color.White)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text("Configurar Perfil", color = VerdeApp, fontSize = 36.sp, fontWeight = FontWeight.Bold)
            Text("Optimiza tus metas diarias y preferencias nutricionales.", color = TextoGris, fontSize = 14.sp)

            Spacer(modifier = Modifier.height(32.dp))

            // User Info Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = GrisCard),
                border = BorderStroke(1.dp, GrisBorde)
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color.Gray) // Placeholder for profile image
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("Hola, [Usuario]", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text("Miembro desde Octubre 2023", color = TextoGris, fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MetricCard("PESO", "75", "kg", Modifier.weight(1f))
                MetricCard("ALTURA", "178", "cm", Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(12.dp))
            MetricCard("EDAD", "28", "años", Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(32.dp))

            Text("OBJETIVO NUTRICIONAL", color = TextoGris, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            ObjectiveItem("Déficit calórico", Icons.Default.TrendingDown, objetivo == "Déficit calórico") { objetivo = "Déficit calórico" }
            Spacer(modifier = Modifier.height(8.dp))
            ObjectiveItem("Mantenimiento", Icons.Default.TrendingFlat, objetivo == "Mantenimiento") { objetivo = "Mantenimiento" }
            Spacer(modifier = Modifier.height(8.dp))
            ObjectiveItem("Aumento de masa", Icons.Default.TrendingUp, objetivo == "Aumento de masa") { objetivo = "Aumento de masa" }

            Spacer(modifier = Modifier.height(32.dp))

            Text("OBJETIVO DIARIO", color = TextoGris, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = GrisCard),
                border = BorderStroke(1.dp, GrisBorde)
            ) {
                Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("[1900]", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Black)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("KCAL", color = VerdeApp, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(Icons.Default.Bolt, contentDescription = null, tint = VerdeApp)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    LinearProgressIndicator(
                        progress = { 1f },
                        modifier = Modifier.fillMaxWidth().height(6.dp).clip(CircleShape),
                        color = VerdeApp,
                        trackColor = GrisBorde
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("OBJETIVOS DE MACRONUTRIENTES", color = TextoGris, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
                Text("Ajuste sugerido por IA", color = TextoGris, fontSize = 11.sp, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
            }
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = GrisCard),
                border = BorderStroke(1.dp, GrisBorde)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    MacroGoalRow("Proteínas", "110g / 130g", 0.85f)
                    Spacer(modifier = Modifier.height(24.dp))
                    MacroGoalRow("Carbohidratos", "250g / 280g", 0.90f)
                    Spacer(modifier = Modifier.height(24.dp))
                    MacroGoalRow("Grasas", "70g / 60g", 1f, isOver = true)
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.TrendingUp, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color(0xFFFFB7B7))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Exceso detectado basado en tu plan de déficit.", color = Color(0xFFFFB7B7), fontSize = 10.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            BotonPrincipal("Guardar cambios", onClick = onSave)
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun MetricCard(label: String, value: String, unit: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = GrisCard),
        border = BorderStroke(1.dp, GrisBorde)
    ) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(label, color = TextoGris, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Row(verticalAlignment = Alignment.Bottom) {
                Text(value, color = VerdeApp, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(4.dp))
                Text(unit, color = Color.White, fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun ObjectiveItem(label: String, icon: ImageVector, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth().height(56.dp).clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        color = GrisCard,
        border = BorderStroke(1.dp, if (isSelected) VerdeApp else GrisBorde)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = if (isSelected) VerdeApp else Color.White)
            Spacer(modifier = Modifier.width(16.dp))
            Text(label, color = Color.White, modifier = Modifier.weight(1f))
            RadioButton(
                selected = isSelected, 
                onClick = onClick,
                colors = RadioButtonDefaults.colors(selectedColor = VerdeApp, unselectedColor = TextoGris)
            )
        }
    }
}

@Composable
fun MacroGoalRow(label: String, value: String, progress: Float, isOver: Boolean = false) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            val parts = value.split(" / ")
            Row {
                Text(parts[0], color = Color.White, fontSize = 14.sp)
                Text(" / ", color = TextoGris, fontSize = 14.sp)
                Text(parts[1], color = if (isOver) Color(0xFF50FFD1) else VerdeApp, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
            color = if (isOver) Color(0xFFFFB7B7) else VerdeApp,
            trackColor = GrisBorde
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PerfilPreview() {
    MyApplicationTheme {
        PerfilScreen(onBack = {}, onSave = {}, onNavigate = {})
    }
}
