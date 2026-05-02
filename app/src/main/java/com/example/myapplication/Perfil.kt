package com.example.myapplication

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(onBack: () -> Unit, onSave: () -> Unit, onNavigate: (String) -> Unit) {
    var objetivo by remember { mutableStateOf("Déficit") }

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
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Search, contentDescription = "Buscar")
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
                    selected = false, 
                    onClick = { /* onNavigate("planes") */ }, 
                    icon = { Icon(Icons.Default.DateRange, null) }, 
                    label = { Text("PLANES") }
                )
                NavigationBarItem(
                    selected = false, 
                    onClick = { /* onNavigate("historial") */ }, 
                    icon = { Icon(Icons.AutoMirrored.Filled.List, null) }, 
                    label = { Text("HISTORIAL") }
                )
                NavigationBarItem(
                    selected = true, 
                    onClick = { }, 
                    icon = { Icon(Icons.Default.Person, null) }, 
                    label = { Text("PERFIL") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black, 
                        indicatorColor = VerdeApp
                    )
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
                text = "Configurar Perfil",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Cuadro de información de usuario
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Black, RoundedCornerShape(24.dp)),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Icono de perfil
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .border(1.dp, Color.Black, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(50.dp),
                            tint = Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        InfoRow(label = "Edad:", value = "28")
                        InfoRow(label = "Peso (kg):", value = "75")
                        InfoRow(label = "Altura (cm):", value = "178")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Objetivo Nutricional", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ObjetivoRadio(label = "Déficit\ncalórico", selected = objetivo == "Déficit", onClick = { objetivo = "Déficit" })
                ObjetivoRadio(label = "Mantenimiento", selected = objetivo == "Mantenimiento", onClick = { objetivo = "Mantenimiento" })
                ObjetivoRadio(label = "Aumento\nde masa", selected = objetivo == "Aumento", onClick = { objetivo = "Aumento" })
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), thickness = 1.dp, color = Color.LightGray)

            Text(text = "Calorías objetivo diarias:", fontSize = 16.sp)
            Text(text = "[1900] KCAL", fontSize = 32.sp, fontWeight = FontWeight.Black)

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), thickness = 1.dp, color = Color.LightGray)

            Text(text = "Objetivos de macronutrientes", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    MacroProgress(label = "Proteínas", current = "110g", target = "130g", progress = 0.85f, percentage = "85%")
                    MacroProgress(label = "Carbohidratos", current = "250g", target = "280g", progress = 0.90f, percentage = "90%")
                    MacroProgress(label = "Grasas", current = "70g", target = "60g", progress = 1.0f, percentage = "110%", isOver = true)
                }
                
                Column(
                    modifier = Modifier.width(100.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(modifier = Modifier.size(80.dp), contentAlignment = Alignment.Center) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            drawArc(
                                color = Color(0xFFF0F0F0),
                                startAngle = 0f,
                                sweepAngle = 360f,
                                useCenter = true
                            )
                            drawArc(
                                color = VerdeApp,
                                startAngle = -90f,
                                sweepAngle = 270f,
                                useCenter = true
                            )
                            drawArc(
                                color = Color.Black,
                                startAngle = -90f,
                                sweepAngle = 360f,
                                useCenter = false,
                                style = Stroke(width = 1.dp.toPx())
                            )
                        }
                    }
                    Text("Macros", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            BotonPrincipal(text = "Guardar cambios", onClick = onSave)
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 16.sp)
        Box(modifier = Modifier.drawBehind {
            val strokeWidthPx = 1.dp.toPx()
            val y = size.height - strokeWidthPx
            drawLine(
                color = Color.LightGray,
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = strokeWidthPx
            )
        }) {
            Text(
                text = "[$value]",
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}

@Composable
fun ObjetivoRadio(label: String, selected: Boolean, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(selectedColor = Color.Black)
        )
        Text(
            text = label,
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
            lineHeight = 12.sp
        )
    }
}

@Composable
fun MacroProgress(label: String, current: String, target: String, progress: Float, percentage: String, isOver: Boolean = false) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, fontSize = 12.sp)
            Text("$current / [$target]", fontSize = 12.sp)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .border(1.dp, Color.Black, RoundedCornerShape(6.dp))
                .clip(RoundedCornerShape(6.dp))
                .background(Color.White)
        ) {
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxSize(),
                color = if (isOver) Color(0xFFFFB7B7) else VerdeApp,
                trackColor = Color.Transparent,
            )
            Text(
                text = percentage,
                modifier = Modifier.align(Alignment.Center),
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PerfilPreview() {
    MyApplicationTheme {
        PerfilScreen(onBack = {}, onSave = {}, onNavigate = {})
    }
}
