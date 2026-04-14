package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Colores principales de la app
val VerdeApp = Color(0xFF7FFFD4)
val GrisFondo = Color(0xFFF7F9FB)
val TextoOscuro = Color(0xFF2D3142)

/**
 * El Logo circular que aparece en Login y Registro
 */
@Composable
fun LogoPrincipal(size: Int = 140) {
    Surface(
        modifier = Modifier.size(size.dp).shadow(8.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        color = Color.White
    ) {
        Image(
            painter = painterResource(id = R.drawable.miplatoimggg),
            contentDescription = "Logo",
            modifier = Modifier.padding((size/7).dp)
        )
    }
}

/**
 * Campo de texto común para toda la app
 */
@Composable
fun CampoTexto(label: String, value: String, onValueChange: (String) -> Unit, isPassword: Boolean = false) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        singleLine = true,
        visualTransformation = if (isPassword) androidx.compose.ui.text.input.PasswordVisualTransformation() 
                               else androidx.compose.ui.text.input.VisualTransformation.None
    )
}

/**
 * Botón principal de color verde
 */
@Composable
fun BotonPrincipal(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = VerdeApp),
        shape = RoundedCornerShape(28.dp)
    ) {
        Text(text = text, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 18.sp)
    }
}
