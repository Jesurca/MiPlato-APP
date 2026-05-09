package com.example.myapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Save
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
fun PlanesScreen(onBack: () -> Unit, onSave: () -> Unit, onNavigate: (String) -> Unit) {
    var selectedTab by remember { mutableStateOf("Déficit Calórico") }

    Scaffold(
        containerColor = FondoOscuro,
        bottomBar = { BarraNavegacionComun("planes", onNavigate) }
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
                            .clip(CircleShape)
                            .background(Color.Gray) // Placeholder icon
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("MiPlato", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
                Icon(Icons.Default.NotificationsNone, contentDescription = null, tint = Color.White)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text("Mis Planes de Alimentación", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
            
            Spacer(modifier = Modifier.height(16.dp))

            // Tab Selector
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                PlanTabItem("Déficit Calórico", selectedTab == "Déficit Calórico") { selectedTab = "Déficit Calórico" }
                PlanTabItem("Mantenimiento", selectedTab == "Mantenimiento") { selectedTab = "Mantenimiento" }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Meal Cards
            MealPlanCard(
                type = "DESAYUNO",
                name = "Bowl de Avena",
                description = "Huevo revuelto, avena con frutos rojos, tostada integral.",
                calories = "400 kcal",
                macros = "P: 20g  C: 45g",
                icon = Icons.Default.DeleteOutline // Usando un icono para representar la acción o tipo
            )
            Spacer(modifier = Modifier.height(16.dp))
            MealPlanCard(
                type = "COMIDA",
                name = "Pollo y Quinoa",
                description = "Pollo a la plancha, quinoa cocida, ensalada de espinacas y brócoli.",
                calories = "650 kcal",
                macros = "P: 45g  C: 60g",
                icon = Icons.Default.Restaurant
            )
            Spacer(modifier = Modifier.height(16.dp))
            MealPlanCard(
                type = "CENA",
                name = "Salmón Asado",
                description = "Salmón al horno con espárragos y una pequeña porción de batata.",
                calories = "500 kcal",
                macros = "P: 35g  C: 15g",
                icon = Icons.Default.Restaurant // Placeholder icon
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Distribution Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = GrisCard),
                border = BorderStroke(1.dp, GrisBorde)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("Distribución Recomendada", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    
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
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("1550", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Black)
                                Text("KCAL / DÍA", color = TextoGris, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        
                        Spacer(modifier = Modifier.width(24.dp))
                        
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(1f)) {
                            MacroDistributionRow("Proteínas", "45%", VerdeApp)
                            MacroDistributionRow("Carbohidratos", "30%", Color(0xFFFFD150))
                            MacroDistributionRow("Grasas", "25%", Color.Gray)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            
            // Footer Metrics
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                MetricSummaryRow("NÚMERO DE COMIDAS", "4 Comidas al día")
                MetricSummaryRow("KCAL DIARIAS OBJETIVO", "1,550 Kcal")
            }

            Spacer(modifier = Modifier.height(32.dp))

            BotonPrincipal("Guardar", onClick = onSave, icon = Icons.Default.Save)
            
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Ver más sugerencias", 
                color = VerdeApp, 
                modifier = Modifier.fillMaxWidth().clickable { },
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                fontSize = 14.sp,
                textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun PlanTabItem(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.height(36.dp).clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        color = if (isSelected) VerdeApp else GrisBorde
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(label, color = if (isSelected) Color.Black else Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun MealPlanCard(type: String, name: String, description: String, calories: String, macros: String, icon: ImageVector) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = GrisCard),
        border = BorderStroke(1.dp, GrisBorde)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(type, color = VerdeApp, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                Icon(icon, contentDescription = null, tint = VerdeApp, modifier = Modifier.size(20.dp))
            }
            Text(name, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(description, color = TextoGris, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(calories, color = VerdeApp, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Surface(color = GrisBorde, shape = RoundedCornerShape(4.dp)) {
                    Text(macros, color = TextoGris, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun MacroDistributionRow(label: String, percentage: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(color))
            Spacer(modifier = Modifier.width(8.dp))
            Text(label, color = TextoGris, fontSize = 12.sp)
        }
        Text(percentage, color = VerdeApp, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun MetricSummaryRow(label: String, value: String) {
    Column {
        Text(label, color = TextoGris, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(value, color = VerdeApp, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(GrisBorde))
    }
}

@Preview(showBackground = true)
@Composable
fun PlanesPreview() {
    MyApplicationTheme {
        PlanesScreen(onBack = {}, onSave = {}, onNavigate = {})
    }
}
