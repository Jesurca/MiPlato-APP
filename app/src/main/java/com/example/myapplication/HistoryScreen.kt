package com.example.myapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun HistoryScreen(onNavigate: (String) -> Unit) {
    val historyData = listOf(
        DayRecord("Lunes 23 oct", 1850, 110, 250, 70),
        DayRecord("Martes 22 oct (Hoy)", 2100, 120, 280, 80, isActual = true),
        DayRecord("Miércoles 21 oct", 1950, 105, 240, 75)
    )

    Scaffold(
        containerColor = FondoOscuro,
        bottomBar = { BarraNavegacionComun("history", onNavigate) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
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
                            .clip(CircleShape)
                            .border(1.dp, VerdeApp, CircleShape)
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(androidx.compose.material.icons.Icons.Default.PhotoCamera, contentDescription = null, modifier = Modifier.size(16.dp), tint = VerdeApp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("MiPlato", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
                Icon(Icons.Default.NotificationsNone, contentDescription = null, tint = Color.White)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text("Historial de Consumo", color = VerdeApp, fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Text("Resumen de tu nutrición semanal", color = TextoGris, fontSize = 14.sp)

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(historyData) { record ->
                    HistoryCard(record)
                }
                
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    DetailExpandedCard()
                }
                
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("ARTÍCULOS CONSUMIDOS", color = TextoGris, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))
                    ConsumedItemRow("Ensalada de Pollo", "Almuerzo • 450 kcal")
                    Spacer(modifier = Modifier.height(12.dp))
                    ConsumedItemRow("Pasta Integral", "Cena • 620 kcal")
                }
            }
        }
    }
}

@Composable
fun HistoryCard(record: DayRecord) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = GrisCard),
        border = BorderStroke(1.dp, GrisBorde)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(record.date, color = TextoGris, fontSize = 12.sp)
                Text("${record.kcal} KCAL", color = if (record.isActual) VerdeApp else Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (record.isActual) {
                    Surface(
                        color = VerdeApp.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Actual", color = VerdeApp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextoGris)
            }
        }
    }
}

@Composable
fun DetailExpandedCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = GrisCard),
        border = BorderStroke(1.dp, GrisBorde)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Detalle: Lunes 23 oct", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Column(horizontalAlignment = Alignment.End) {
                    Text("TOTAL", color = TextoGris, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Text("1,850", color = VerdeApp, fontSize = 24.sp, fontWeight = FontWeight.Black)
                    Text("kcal", color = VerdeApp, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(120.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        progress = { 0.7f },
                        modifier = Modifier.fillMaxSize(),
                        color = VerdeApp,
                        strokeWidth = 10.dp,
                        trackColor = GrisBorde
                    )
                    Text("Macros", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
                
                Spacer(modifier = Modifier.width(24.dp))
                
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    MacroDetailItem("Proteínas", "110g", VerdeApp)
                    MacroDetailItem("Carbohidratos", "250g", Color(0xFFFFD150))
                    MacroDetailItem("Grasas", "70g", Color.Gray)
                }
            }
        }
    }
}

@Composable
fun MacroDetailItem(label: String, value: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(color))
        Spacer(modifier = Modifier.width(8.dp))
        Text(label, color = TextoGris, fontSize = 12.sp, modifier = Modifier.width(100.dp))
        Text(value, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ConsumedItemRow(name: String, detail: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = GrisCard),
        border = BorderStroke(1.dp, GrisBorde)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(GrisBorde) // Espacio para imagen de comida
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(name, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(detail, color = TextoGris, fontSize = 12.sp)
            }
            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = VerdeApp, modifier = Modifier.size(20.dp))
        }
    }
}

data class DayRecord(
    val date: String,
    val kcal: Int,
    val pro: Int,
    val carb: Int,
    val fat: Int,
    val isActual: Boolean = false
)

@Preview(showBackground = true)
@Composable
fun HistoryPreview() {
    MyApplicationTheme {
        HistoryScreen(onNavigate = {})
    }
}
