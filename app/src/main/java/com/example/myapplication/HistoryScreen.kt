package com.example.myapplication

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

/**
 * HistoryScreen: Pantalla que muestra el historial de consumo diario.
 * Permite visualizar el total de KCAL por día y el desglose de macros de forma expandible.
 */
@Composable
fun HistoryScreen(onBack: () -> Unit) {
    // Lista de datos simulados para el historial
    val historyData = listOf(
        DayRecord("Lunes 23 oct", 1850, 110, 250, 70),
        DayRecord("Martes 22 oct", 2100, 120, 280, 80),
        DayRecord("Miércoles 21 oct", 1950, 105, 240, 75),
        DayRecord("Jueves 20 oct", 1850, 110, 250, 70),
        DayRecord("Viernes 19 oct", 2200, 130, 300, 85)
    )

    Scaffold(
        bottomBar = {
            // Reutilizamos el estilo de la barra de navegación pero marcando HISTORIAL como seleccionado
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(selected = false, onClick = { /* Ir a Inicio */ }, icon = { Icon(Icons.Default.Home, null) }, label = { Text("INICIO") })
                NavigationBarItem(selected = false, onClick = { /* Ir a Planes */ }, icon = { Icon(Icons.Default.DateRange, null) }, label = { Text("PLANES") })
                NavigationBarItem(
                    selected = true, 
                    onClick = {}, 
                    icon = { Icon(Icons.Default.Favorite, null) },
                    label = { Text("HISTORIAL") },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = Color.Black, indicatorColor = VerdeApp)
                )
                NavigationBarItem(selected = false, onClick = { /* Ir a Perfil */ }, icon = { Icon(Icons.Default.Person, null) }, label = { Text("PERFIL") })
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF0F7FF)) // Fondo azul claro consistente
                .padding(24.dp)
        ) {
            // Cabecera superior: Botón volver, Logo y Búsqueda
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(Icons.Default.ArrowBack, null, modifier = Modifier.clickable { onBack() })
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(id = R.drawable.miplatoimggg), contentDescription = null, modifier = Modifier.size(24.dp), tint = Color.Unspecified)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("MiPlato", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.Gray)
                }
                
                Icon(Icons.Default.Search, null)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(text = "Historial de Consumo", fontSize = 28.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))

            // Imagen del gráfico histórico añadida
            Image(
                painter = painterResource(id = R.drawable.grafic_history),
                contentDescription = "Gráfico Histórico",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Lista de días consumidos usando LazyColumn para mejor rendimiento
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(1.dp) // Espaciado mínimo para simular líneas divisorias
            ) {
                items(historyData) { record ->
                    HistoryItem(record)
                }
            }
        }
    }
}

/**
 * Representa cada fila del historial. Tiene un estado de expansión para mostrar macros.
 */
@Composable
fun HistoryItem(record: DayRecord) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(0.5.dp, Color.LightGray)
            .clickable { isExpanded = !isExpanded }
            .padding(16.dp)
    ) {
        if (!isExpanded) {
            // Vista contraída: Fecha y KCAL totales
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = record.date, fontWeight = FontWeight.Medium, fontSize = 18.sp)
                Text(text = "${record.kcal} KCAL", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        } else {
            // Vista expandida: Fecha arriba y detalles de macros abajo
            Text(text = record.date, fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(bottom = 16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Datos numéricos de Macros
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    MacroRow("Proteínas", "${record.pro}g")
                    MacroRow("Carbohidratos", "${record.carb}g")
                    MacroRow("Grasas", "${record.fat}g")
                }

                // Gráfico de pastel (Pie Chart) simplificado con Canvas
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(modifier = Modifier.size(80.dp), contentAlignment = Alignment.Center) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            // Dibujamos un círculo seccionado simple
                            drawArc(color = VerdeApp, startAngle = 0f, sweepAngle = 270f, useCenter = true)
                            drawArc(color = Color(0xFF2D3142), startAngle = 270f, sweepAngle = 90f, useCenter = true)
                            drawCircle(color = Color.Black, radius = size.minDimension / 2, style = Stroke(width = 1.dp.toPx()))
                        }
                    }
                    Text("Distribución\nde Macros", fontSize = 12.sp, textAlign = androidx.compose.ui.text.style.TextAlign.Center, lineHeight = 14.sp)
                }
            }
        }
    }
}

@Composable
fun MacroRow(label: String, value: String) {
    Column {
        Text(text = "$label: $value", fontSize = 16.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(2.dp))
        Box(modifier = Modifier.width(120.dp).height(1.dp).background(Color.LightGray))
    }
}

// Clase de datos simple para organizar la información de cada día
data class DayRecord(
    val date: String,
    val kcal: Int,
    val pro: Int,
    val carb: Int,
    val fat: Int
)

@Preview(showBackground = true)
@Composable
fun HistoryPreview() {
    MyApplicationTheme {
        HistoryScreen(onBack = {})
    }
}
