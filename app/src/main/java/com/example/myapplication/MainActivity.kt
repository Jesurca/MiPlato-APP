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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
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

                Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
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
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LogoPrincipal()
        Spacer(modifier = Modifier.height(32.dp))
        Text("MiPlato Login", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(32.dp))
        CampoTexto("Gmail", email, { email = it })
        Spacer(modifier = Modifier.height(16.dp))
        CampoTexto("Contraseña", pass, { pass = it }, isPassword = true)
        Spacer(modifier = Modifier.height(48.dp))
        BotonPrincipal("Iniciar Sesión") { onLogin(email, pass) }
        Spacer(modifier = Modifier.height(24.dp))
        Text("¿Eres nuevo? Regístrate aquí",
            modifier = Modifier.clickable { onGoRegister() },
            color = Color.Gray, fontWeight = FontWeight.Bold)
    }
}
