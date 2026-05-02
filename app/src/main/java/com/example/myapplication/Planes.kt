package com.example.myapplication

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanesScreen(onBack: () -> Unit, onSave: () -> Unit, onNavigate: (String) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(end = 48.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.miplatoimggg),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("MiPlato", color = Color.Gray, fontSize = 18.sp)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigate("home") },
                    icon = { Icon(Icons.Default.Home, null) },
                    label = { Text("INICIO") }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Default.DateRange, null) },
                    label = { Text("PLANES") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black,
                        indicatorColor = VerdeApp
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { /* onNavigate("historial") */ },
                    icon = { Icon(Icons.AutoMirrored.Filled.List, null) },
                    label = { Text("HISTORIAL") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigate("perfil") },
                    icon = { Icon(Icons.Default.Person, null) },
                    label = { Text("AJUSTES") }
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Planes",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )

            Text(
                text = "Mis Planes de Alimentación",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Surface(
                color = VerdeApp,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Déficit Calórico or Mantenimiento",
                    modifier = Modifier.padding(12.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Recomendaciones Diarias",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PlanMealCard(
                    title = "Desayuno",
                    icon = Icons.Default.Fastfood,
                    items = listOf("Huevo revuelto, avena", "Huevo, tlanhas"),
                    calories = "400",
                    protein = "20g"
                )
                PlanMealCard(
                    title = "Comida",
                    icon = Icons.Default.Dining,
                    items = listOf("Pollo a la plancha, arroz integral, verduras"),
                    calories = "400",
                    protein = "20g"
                )
                PlanMealCard(
                    title = "Cena",
                    icon = Icons.Default.SetMeal,
                    items = listOf("Pescado, ensalada"),
                    calories = "300",
                    protein = "25g"
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "[Kcal diarias objetivo]", fontSize = 12.sp)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                            .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
                            .padding(8.dp)
                    ) {
                        Text(text = "[Kcal diarias objetivo]", fontSize = 12.sp, color = Color.Gray)
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "[Número de comidas]", fontSize = 12.sp)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                            .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
                            .padding(8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "", modifier = Modifier.weight(1f))
                            Icon(Icons.Default.KeyboardArrowDown, contentDescription = null)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Donut Chart placeholder
                Box(modifier = Modifier.size(80.dp), contentAlignment = Alignment.Center) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawArc(Color(0xFFF0F0F0), 0f, 360f, true)
                        drawArc(VerdeApp, -90f, 120f, true)
                        drawArc(VerdeApp.copy(alpha = 0.6f), 30f, 100f, true)
                        drawArc(VerdeApp.copy(alpha = 0.3f), 130f, 140f, true)
                        drawArc(Color.Black, 0f, 360f, false, style = Stroke(1.dp.toPx()))
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(text = "[Distribución Macro Recomendada]", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "g/dia", fontSize = 10.sp)
                        Box(modifier = Modifier.width(50.dp).padding(horizontal = 4.dp).border(1.dp, Color.Black, RoundedCornerShape(8.dp)).padding(4.dp))
                        Text(text = "g/dia", fontSize = 10.sp)
                    }
                    Text(text = "[Carb., Prot., Grasas]", fontSize = 10.sp, modifier = Modifier.padding(top = 4.dp))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            BotonPrincipal(text = "Guardar", onClick = onSave)

            Text(
                text = "Ver más sugerencias",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Center,
                textDecoration = TextDecoration.Underline,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun PlanMealCard(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, items: List<String>, calories: String, protein: String) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            items.forEach { item ->
                Text(text = "• [$item]", fontSize = 11.sp, lineHeight = 14.sp)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "[Calorias: $calories]", fontSize = 11.sp)
            Text(text = "[Proteinas: $protein]", fontSize = 11.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlanesPreview() {
    MyApplicationTheme {
        PlanesScreen(onBack = {}, onSave = {}, onNavigate = {})
    }
}
