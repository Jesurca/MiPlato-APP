package com.example.myapplication

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlimentoCapturadoScreen(
    capturedUri: Uri?,
    onBack: () -> Unit,
    onAgregar: () -> Unit
) {
    Scaffold(
        containerColor = FondoOscuro,
        topBar = {
            TopAppBar(
                title = { Text("Alimento Detectado", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = FondoOscuro)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(250.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(GrisCard)
            ) {
                if (capturedUri != null) {
                    AsyncImage(
                        model = capturedUri,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            
            Text("Pollo a la Plancha", color = Color.White, fontSize = 24.sp)
            Text("~ 350 kcal", color = VerdeApp, fontSize = 18.sp)
            
            Spacer(modifier = Modifier.weight(1f))
            
            BotonPrincipal(text = "AGREGAR AL DIARIO", onClick = onAgregar)
        }
    }
}
