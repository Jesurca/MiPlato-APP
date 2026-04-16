package com.example.myapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

/**
 * AddFoodScreen: Pantalla para buscar y añadir alimentos manualmente.
 * Permite seleccionar cantidades y unidades de medida.
 */
@Composable
fun AddFoodScreen(onBack: () -> Unit, onFoodAdded: () -> Unit) {
    var searchText by remember { mutableStateOf("") }
    var quantity by remember { mutableIntStateOf(1) }
    var selectedUnit by remember { mutableStateOf("Gramos (g)") }
    var expanded by remember { mutableStateOf(false) }

    val units = listOf("Gramos (g)", "Porción", "Taza")
    val searchResults = listOf("Pollo a la plancha", "Arroz integral", "Ensalada mixta", "Manzana")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F7FF)) 
            .padding(24.dp)
    ) {
        // Cabecera
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver",
                modifier = Modifier.size(24.dp).clickable { onBack() }
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "MiPlato", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(24.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Buscador
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = { Text("Buscar alimentos...") },
            modifier = Modifier.fillMaxWidth().background(Color.White, RoundedCornerShape(12.dp)),
            leadingIcon = { Icon(Icons.Default.Search, null) },
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Resultados", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(12.dp))

        // Lista de resultados
        Column(modifier = Modifier.fillMaxWidth().weight(1f)) {
            searchResults.forEach { food ->
                ItemComidaBusqueda(food)
            }
        }

        // Detalles de cantidad
        Text(text = "Cantidad", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Selector + / -
            Row(
                modifier = Modifier
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                    .background(Color.White, RoundedCornerShape(8.dp)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { if (quantity > 1) quantity-- }) {
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = null)
                }
                Text(text = quantity.toString(), fontWeight = FontWeight.Bold)
                IconButton(onClick = { quantity++ }) {
                    Icon(Icons.Default.KeyboardArrowUp, contentDescription = null)
                }
            }

            // Dropdown de unidades
            Box(modifier = Modifier.weight(1f)) {
                OutlinedCard(
                    onClick = { expanded = true },
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.outlinedCardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = selectedUnit, fontSize = 14.sp)
                        Icon(Icons.Default.ArrowDropDown, null)
                    }
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    units.forEach { unit ->
                        DropdownMenuItem(
                            text = { Text(unit) },
                            onClick = { selectedUnit = unit; expanded = false }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        BotonPrincipal(text = "Agregar alimento", onClick = onFoodAdded)
    }
}

@Composable
fun ItemComidaBusqueda(name: String) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.List, null, modifier = Modifier.size(24.dp), tint = Color.Gray)
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = name, fontSize = 16.sp)
            }
            Icon(Icons.Default.Add, null, tint = Color.Gray)
        }
        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
    }
}

@Preview(showBackground = true)
@Composable
fun AddFoodPreview() {
    MyApplicationTheme {
        AddFoodScreen(onBack = {}, onFoodAdded = {})
    }
}
