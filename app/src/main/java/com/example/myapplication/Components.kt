package com.example.myapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// COLORES DEL TEMA OSCURO (Extraídos de los screenshots)
val VerdeApp = Color(0xFF50FFD1) 
val FondoOscuro = Color(0xFF0C0F0E)
val GrisCard = Color(0xFF171C1B)
val GrisBorde = Color(0xFF262D2B)
val TextoGris = Color(0xFF8E9997)
val RojoAlerta = Color(0xFFFFB7B7)
val AmarilloMacros = Color(0xFFFFD150)

/**
 * Logo con fondo blanco para contraste
 */
@Composable
fun LogoPrincipal(size: Int = 100) {
    Box(
        modifier = Modifier
            .size(size.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.miplatoimggg),
            contentDescription = "Logo",
            modifier = Modifier.fillMaxSize()
        )
    }
}

/**
 * Campo de texto estilizado (Label en verde arriba, input en gris oscuro)
 */
@Composable
fun CampoTexto(
    label: String, 
    value: String, 
    onValueChange: (String) -> Unit, 
    isPassword: Boolean = false,
    icon: ImageVector? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label.uppercase(),
            color = VerdeApp,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text("[Escribe aquí...]", color = TextoGris, fontSize = 14.sp) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            leadingIcon = icon?.let { { Icon(it, contentDescription = null, tint = TextoGris, modifier = Modifier.size(20.dp)) } },
            trailingIcon = trailingIcon,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = GrisCard,
                focusedContainerColor = GrisCard,
                focusedBorderColor = VerdeApp,
                unfocusedBorderColor = GrisBorde,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = VerdeApp
            ),
            visualTransformation = if (isPassword) androidx.compose.ui.text.input.PasswordVisualTransformation() 
                                   else androidx.compose.ui.text.input.VisualTransformation.None
        )
    }
}

/**
 * Botón principal con sombra verde y bordes redondeados
 */
@Composable
fun BotonPrincipal(
    text: String, 
    icon: ImageVector? = null,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(elevation = 12.dp, shape = RoundedCornerShape(28.dp), spotColor = VerdeApp),
        colors = ButtonDefaults.buttonColors(containerColor = VerdeApp),
        shape = RoundedCornerShape(28.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (icon != null) {
                Icon(icon, contentDescription = null, tint = Color.Black, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(10.dp))
            }
            Text(text = text, color = Color.Black, fontWeight = FontWeight.Black, fontSize = 16.sp)
        }
    }
}

/**
 * Barra de navegación con indicador de punto verde
 */
@Composable
fun BarraNavegacionComun(currentScreen: String, onNavigate: (String) -> Unit) {
    NavigationBar(
        containerColor = FondoOscuro,
        tonalElevation = 0.dp,
        modifier = Modifier.border(BorderStroke(0.5.dp, GrisBorde), RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
    ) {
        val items = listOf(
            Triple("home", Icons.Default.Home, "Inicio"),
            Triple("planes", Icons.Default.RestaurantMenu, "Planes"),
            Triple("history", Icons.Default.History, "Historial"),
            Triple("perfil", Icons.Default.Person, "Perfil")
        )

        items.forEach { (route, icon, label) ->
            val isSelected = currentScreen == route
            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(route) },
                icon = { 
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            icon, 
                            contentDescription = label, 
                            tint = if (isSelected) VerdeApp else TextoGris,
                            modifier = Modifier.size(24.dp)
                        )
                        if (isSelected) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Box(modifier = Modifier.size(4.dp).background(VerdeApp, CircleShape))
                        }
                    }
                },
                label = { Text(label, color = if (isSelected) VerdeApp else TextoGris, fontSize = 10.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}
