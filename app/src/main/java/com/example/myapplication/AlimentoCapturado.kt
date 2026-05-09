package com.example.myapplication

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myapplication.ui.theme.MyApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlimentoCapturadoScreen(capturedUri: Uri?, onBack: () -> Unit, onAgregar: () -> Unit) {
    Scaffold(
        containerColor = FondoOscuro,
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(VerdeApp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Restaurant, contentDescription = null, modifier = Modifier.size(18.dp), tint = Color.Black)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("MiPlato", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.NotificationsNone, contentDescription = null, tint = VerdeApp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        bottomBar = { BarraNavegacionComun("history", {}) } // Dummy navigation for visual consistency
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Text(
                text = "Pollo con arroz",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Main Image with Overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                if (capturedUri != null) {
                    AsyncImage(
                        model = capturedUri,
                        contentDescription = "Alimento capturado",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // Placeholder box matching the screenshot's dark aesthetic
                    Box(modifier = Modifier.fillMaxSize().background(GrisCard))
                }
                
                // Dark overlay at the bottom of the image
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                        .fillMaxWidth(0.6f)
                        .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
                        .border(1.dp, GrisBorde, RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Column {
                        Text("CONTENIDO\nESTIMADO", color = TextoGris, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Text("500", color = VerdeApp, fontSize = 32.sp, fontWeight = FontWeight.Black)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("KCAL", color = VerdeApp, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(Icons.Default.Bolt, contentDescription = null, tint = VerdeApp, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Alta\nDensidad", color = VerdeApp, fontSize = 10.sp, fontWeight = FontWeight.Bold, lineHeight = 10.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Macro Circular Indicators
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MacroCircleItem("PROTEÍNAS", "35g", 0.7f, VerdeApp)
                MacroCircleItem("CARBOHIDRATOS", "55g", 0.6f, VerdeApp)
                MacroCircleItem("GRASAS", "15g", 0.4f, VerdeApp)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Additional Details
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = GrisCard),
                border = BorderStroke(1.dp, GrisBorde)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Restaurant, contentDescription = null, tint = VerdeApp, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("DETALLES ADICIONALES", color = VerdeApp, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Row(modifier = Modifier.fillMaxWidth()) {
                        DetailItem("FIBRA", "15g", Modifier.weight(1f))
                        DetailItem("AZÚCARES", "35g", Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        DetailItem("SODIO", "20g", Modifier.weight(1f))
                        DetailItem("POTASIO", "450mg", Modifier.weight(1f))
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            BotonPrincipal(text = "Agregar al día", onClick = onAgregar, icon = Icons.Default.Restaurant)
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun MacroCircleItem(label: String, value: String, progress: Float, color: Color) {
    Card(
        modifier = Modifier.size(100.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = GrisCard),
        border = BorderStroke(1.dp, GrisBorde)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(modifier = Modifier.size(40.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxSize(),
                    color = color,
                    strokeWidth = 4.dp,
                    trackColor = GrisBorde
                )
                Icon(Icons.Default.Bolt, contentDescription = null, tint = color, modifier = Modifier.size(12.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(label, color = TextoGris, fontSize = 8.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            Text(value, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun DetailItem(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(label, color = TextoGris, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        Text(value, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Box(modifier = Modifier.fillMaxWidth(0.8f).height(1.dp).background(GrisBorde))
    }
}

@Preview(showBackground = true)
@Composable
fun AlimentoCapturadoPreview() {
    MyApplicationTheme {
        AlimentoCapturadoScreen(capturedUri = null, onBack = {}, onAgregar = {})
    }
}
