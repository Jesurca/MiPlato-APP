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
import androidx.compose.material.icons.filled.*
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
fun AddFoodScreen(onBack: () -> Unit, onFoodAdded: () -> Unit) {
    var searchText by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("100") }
    var selectedUnit by remember { mutableStateOf("Gramos (g)") }
    var expanded by remember { mutableStateOf(false) }

    val units = listOf("Gramos (g)", "Porción", "Taza")
    
    Scaffold(
        containerColor = FondoOscuro,
        bottomBar = { BarraNavegacionComun("history", {}) } // Dummy navigation for visual consistency
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
                            .background(Color.Gray) // Placeholder icon
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("MiPlato", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
                Icon(Icons.Default.NotificationsNone, contentDescription = null, tint = VerdeApp)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Search Bar
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Buscar alimentos...", color = TextoGris) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(GrisCard, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                leadingIcon = { Icon(Icons.Default.Search, null, tint = TextoGris) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = VerdeApp,
                    unfocusedBorderColor = GrisBorde,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text("RESULTADOS DE BÚSQUEDA", color = TextoGris, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            // Search Results
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = GrisCard),
                border = BorderStroke(1.dp, GrisBorde)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    SearchResultItem("Pollo a la plancha", "165 kcal • 100g", Icons.Default.Restaurant)
                    HorizontalDivider(color = GrisBorde, modifier = Modifier.padding(vertical = 8.dp))
                    SearchResultItem("Arroz integral", "111 kcal • 100g", Icons.Default.Grain)
                    HorizontalDivider(color = GrisBorde, modifier = Modifier.padding(vertical = 8.dp))
                    SearchResultItem("Ensalada mixta", "45 kcal • 150g", Icons.Default.Eco)
                    HorizontalDivider(color = GrisBorde, modifier = Modifier.padding(vertical = 8.dp))
                    SearchResultItem("Manzana", "52 kcal • 1 ud", Icons.Default.Favorite)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Quantity Details Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = GrisCard),
                border = BorderStroke(1.dp, GrisBorde)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("Detalles de cantidad", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text("Ajusta la porción para calcular macros precisos.", color = TextoGris, fontSize = 14.sp)
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Text("Cantidad", color = TextoGris, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = quantity,
                        onValueChange = { quantity = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        leadingIcon = { Icon(Icons.Default.Remove, null, tint = VerdeApp, modifier = Modifier.clickable { /* Decrease */ }) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = VerdeApp,
                            unfocusedBorderColor = GrisBorde,
                            focusedContainerColor = FondoOscuro,
                            unfocusedContainerColor = FondoOscuro,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        textStyle = androidx.compose.ui.text.TextStyle(textAlign = androidx.compose.ui.text.style.TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text("Unidad", color = TextoGris, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Surface(
                            modifier = Modifier.fillMaxWidth().height(56.dp).clickable { expanded = true },
                            shape = RoundedCornerShape(12.dp),
                            color = FondoOscuro,
                            border = BorderStroke(1.dp, GrisBorde)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(selectedUnit, color = Color.White)
                                Icon(Icons.Default.KeyboardArrowDown, null, tint = VerdeApp)
                            }
                        }
                        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }, modifier = Modifier.background(GrisCard)) {
                            units.forEach { unit ->
                                DropdownMenuItem(
                                    text = { Text(unit, color = Color.White) },
                                    onClick = { selectedUnit = unit; expanded = false }
                                )
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    BotonPrincipal("Agregar alimento", onClick = onFoodAdded, icon = Icons.Default.CheckCircle)
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    BotonSecundario("Volver a Buscar", onClick = onBack)
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun SearchResultItem(name: String, detail: String, icon: ImageVector) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(GrisBorde),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = VerdeApp, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(name, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(detail, color = TextoGris, fontSize = 12.sp)
            }
        }
        Icon(Icons.Default.Add, contentDescription = null, tint = VerdeApp, modifier = Modifier.size(24.dp).border(1.dp, VerdeApp, CircleShape))
    }
}

@Composable
fun BotonSecundario(text: String, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(56.dp),
        shape = RoundedCornerShape(28.dp),
        border = BorderStroke(1.dp, GrisBorde),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
    ) {
        Text(text, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun AddFoodPreview() {
    MyApplicationTheme {
        AddFoodScreen(onBack = {}, onFoodAdded = {})
    }
}
