package com.example.myapplication

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.viewmodel.AuthState
import com.example.myapplication.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigation() {
    val authViewModel: AuthViewModel = viewModel()
    val context = LocalContext.current
    
    // Estado de la pantalla actual
    var currentScreen by remember { 
        mutableStateOf(if (FirebaseAuth.getInstance().currentUser != null) "home" else "login") 
    }
    
    // Estado para la URI de la imagen capturada
    var capturedUri by remember { mutableStateOf<Uri?>(null) }

    // Lanzador de permisos para la cámara
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) currentScreen = "camera"
        else Toast.makeText(context, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
    }

    // Lógica central de navegación y respuesta a estados de Auth
    LaunchedEffect(authViewModel.authState) {
        when (val state = authViewModel.authState) {
            is AuthState.SuccessRegistration -> {
                currentScreen = "login" // REDIRECCIÓN AL LOGIN TRAS REGISTRO
                authViewModel.resetState()
            }
            is AuthState.SuccessLogin -> {
                currentScreen = "home"
                authViewModel.resetState()
            }
            is AuthState.Error -> {
                // Ya no mostramos Toast. El error se muestra en rojo dentro de las pantallas.
                // No reseteamos el estado aquí para que la pantalla pueda leer el mensaje de error.
            }
            else -> {}
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (currentScreen) {
            "login" -> LoginScreen(
                onLogin = { email, pass -> authViewModel.login(email, pass) },
                onGoRegister = { currentScreen = "register" }
            )
            
            "register" -> RegisterScreen(
                onRegistered = { name, email, pass, obj -> 
                    authViewModel.register(name, email, pass, obj) 
                },
                onBack = { currentScreen = "login" }
            )
            
            "home" -> HomeScreen(
                onScan = {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        currentScreen = "camera"
                    } else {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                },
                onNavigate = { currentScreen = it }
            )
            
            "camera" -> CameraScreen(
                onBack = { currentScreen = "home" },
                onCaptured = { uri -> 
                    capturedUri = uri
                    currentScreen = "captured_food" 
                }
            )
            
            "captured_food" -> AlimentoCapturadoScreen(
                capturedUri = capturedUri,
                onBack = { currentScreen = "camera" },
                onAgregar = { currentScreen = "home" }
            )
            
            "perfil" -> PerfilScreen(
                onBack = { currentScreen = "home" },
                onSave = { currentScreen = "home" },
                onLogout = {
                    authViewModel.logout()
                    currentScreen = "login"
                },
                onNavigate = { currentScreen = it }
            )
            
            "planes" -> PlanesScreen(
                onBack = { currentScreen = "home" },
                onSave = { currentScreen = "home" },
                onNavigate = { currentScreen = it }
            )

            "history" -> HistoryScreen(
                onNavigate = { currentScreen = it }
            )

            "add_food" -> AddFoodScreen(
                onBack = { currentScreen = "history" },
                onFoodAdded = { currentScreen = "history" }
            )
        }

        // Pantalla de carga (Loading)
        if (authViewModel.authState is AuthState.Loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = VerdeApp)
            }
        }
    }
}
