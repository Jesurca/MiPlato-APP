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
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.AuthViewModel
import com.example.myapplication.viewmodel.AuthState
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val authViewModel: AuthViewModel = viewModel()
            val context = LocalContext.current
            
            var screen by remember { 
                mutableStateOf(if (FirebaseAuth.getInstance().currentUser != null) "home" else "login") 
            }
            var capturedUri by remember { mutableStateOf<Uri?>(null) }

            // Observador de AuthViewModel
            LaunchedEffect(authViewModel.authState) {
                when (val state = authViewModel.authState) {
                    is AuthState.SuccessRegistration -> {
                        Toast.makeText(context, "¡Cuenta creada! Inicia sesión", Toast.LENGTH_SHORT).show()
                        screen = "login"
                        authViewModel.resetState()
                    }
                    is AuthState.SuccessLogin -> {
                        screen = "home"
                        authViewModel.resetState()
                    }
                    is AuthState.Error -> {
                        Toast.makeText(context, "Error: ${state.message}", Toast.LENGTH_LONG).show()
                        authViewModel.resetState()
                    }
                    else -> {}
                }
            }

            val permissionLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                if (isGranted) screen = "camera"
                else Toast.makeText(context, "Permiso denegado", Toast.LENGTH_SHORT).show()
            }

            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = FondoOscuro) {
                    if (authViewModel.authState is AuthState.Loading) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = VerdeApp)
                        }
                    } else {
                        when (screen) {
                            "login" -> LoginScreen(
                                onLogin = { email: String, pass: String -> 
                                    authViewModel.login(email, pass) 
                                },
                                onGoRegister = { screen = "register" }
                            )
                            "register" -> RegisterScreen(
                                onRegistered = { name: String, email: String, pass: String, obj: String -> 
                                    authViewModel.register(name, email, pass, obj) 
                                },
                                onBack = { screen = "login" }
                            )
                            "home" -> HomeScreen(
                                onScan = {
                                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                                        screen = "camera"
                                    } else {
                                        permissionLauncher.launch(Manifest.permission.CAMERA)
                                    }
                                },
                                onNavigate = { target: String -> screen = target }
                            )
                            "camera" -> CameraScreen(
                                onBack = { screen = "home" },
                                onCaptured = { uri: Uri -> capturedUri = uri; screen = "captured_food" }
                            )
                            "captured_food" -> AlimentoCapturadoScreen(
                                capturedUri = capturedUri,
                                onBack = { screen = "camera" },
                                onAgregar = { screen = "home" }
                            )
                            "perfil" -> PerfilScreen(
                                onBack = { screen = "home" },
                                onSave = { screen = "home" },
                                onNavigate = { target: String -> screen = target }
                            )
                            "planes" -> PlanesScreen(
                                onBack = { screen = "home" },
                                onSave = { screen = "home" },
                                onNavigate = { target: String -> screen = target }
                            )
                        }
                    }
                }
            }
        }
    }
}
