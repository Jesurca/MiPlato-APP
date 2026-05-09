package com.example.myapplication

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val auth = FirebaseAuth.getInstance()
        auth.signOut()
        
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                var screen by remember { mutableStateOf("login") }
                var capturedUri by remember { mutableStateOf<Uri?>(null) }
                val context = LocalContext.current

                val permissionLauncher = rememberLauncherForActivityResult(
                    ActivityResultContracts.RequestPermission()
                ) { isGranted ->
                    if (isGranted) {
                        screen = "camera"
                    } else {
                        Toast.makeText(context, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
                    }
                }

                Surface(modifier = Modifier.fillMaxSize(), color = FondoOscuro) {
                    when (screen) {
                        "login" -> LoginScreen(
                            onLogin = { email, pass ->
                                if (email.isNotEmpty() && pass.isNotEmpty()) {
                                    auth.signInWithEmailAndPassword(email, pass)
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                screen = "home"
                                            } else {
                                                Toast.makeText(context, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                                            }
                                        }
                                }
                            },
                            onGoRegister = { screen = "register" }
                        )
                        "register" -> RegisterScreen(
                            onRegistered = { email, pass ->
                                if (email.isNotEmpty() && pass.isNotEmpty()) {
                                    auth.createUserWithEmailAndPassword(email, pass)
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                Toast.makeText(context, "¡Cuenta creada!", Toast.LENGTH_SHORT).show()
                                                screen = "login"
                                            }
                                        }
                                }
                            },
                            onBack = { screen = "login" }
                        )
                        "home" -> HomeScreen(
                            onScan = {
                                when (PackageManager.PERMISSION_GRANTED) {
                                    ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) -> {
                                        screen = "camera"
                                    }
                                    else -> {
                                        permissionLauncher.launch(Manifest.permission.CAMERA)
                                    }
                                }
                            },
                            onNavigate = { target -> screen = target }
                        )
                        "camera" -> CameraScreen(
                            onBack = { screen = "home" },
                            onCaptured = { uri -> 
                                capturedUri = uri
                                screen = "captured_food" 
                            }
                        )
                        "captured_food" -> AlimentoCapturadoScreen(
                            capturedUri = capturedUri,
                            onBack = { screen = "camera" },
                            onAgregar = { 
                                Toast.makeText(context, "Alimento agregado con éxito", Toast.LENGTH_SHORT).show()
                                screen = "home" 
                            }
                        )
                        "perfil" -> PerfilScreen(
                            onBack = { screen = "home" },
                            onSave = { screen = "home" },
                            onNavigate = { target -> screen = target }
                        )
                        "planes" -> PlanesScreen(
                            onBack = { screen = "home" },
                            onSave = { screen = "home" },
                            onNavigate = { target -> screen = target }
                        )
                        "history" -> HistoryScreen(onNavigate = { screen = it })
                    }
                }
            }
        }
    }
}

@Composable
fun LoginScreen(onLogin: (String, String) -> Unit, onGoRegister: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoOscuro)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        LogoPrincipal(size = 80)
        Spacer(modifier = Modifier.height(24.dp))
        Text("MiPlato", color = VerdeApp, fontSize = 40.sp, fontWeight = FontWeight.Bold)
        Text(
            "Optimiza tu nutrición con la precisión de la ciencia.",
            color = Color.White,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = GrisCard),
            border = androidx.compose.foundation.BorderStroke(1.dp, GrisBorde)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    "Iniciar sesión",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                CampoTexto("CORREO ELECTRÓNICO", email, { email = it })
                Spacer(modifier = Modifier.height(16.dp))
                CampoTexto(
                    "CONTRASEÑA", 
                    pass, 
                    { pass = it }, 
                    isPassword = true,
                    trailingIcon = { Icon(Icons.Default.VisibilityOff, null, tint = TextoGris) }
                )
                
                Text(
                    "¿Olvidaste tu contraseña?",
                    color = VerdeApp,
                    fontSize = 12.sp,
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    textAlign = TextAlign.End
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                BotonPrincipal("Iniciar sesión") { onLogin(email, pass) }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text("O TAMBIÉN", color = TextoGris, fontSize = 10.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    SocialButton("Google", Modifier.weight(1f))
                    SocialButton("Apple", Modifier.weight(1f))
                }
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        Row {
            Text("¿No tienes cuenta aún? ", color = Color.White)
            Text(
                "Registrarse", 
                color = VerdeApp, 
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onGoRegister() }
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun SocialButton(text: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(12.dp),
        color = GrisCard,
        border = androidx.compose.foundation.BorderStroke(1.dp, GrisBorde)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(modifier = Modifier.size(18.dp).background(Color.White)) // Placeholder icon
            Spacer(modifier = Modifier.width(8.dp))
            Text(text, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
    }
}
