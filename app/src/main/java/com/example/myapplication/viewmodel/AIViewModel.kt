package com.example.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.RecommendedMeal
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

sealed class AIState {
    object Idle : AIState()
    object Loading : AIState()
    data class Success(val recommendations: List<RecommendedMeal>) : AIState()
    data class Error(val message: String) : AIState()
}

class AIViewModel : ViewModel() {
    
    // Al usar gemini-1.5-flash con el SDK 0.9.0, usamos la API Key desde BuildConfig para mayor seguridad.
    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = com.example.myapplication.BuildConfig.GEMINI_API_KEY
    )

    var aiState by mutableStateOf<AIState>(AIState.Idle)
        private set

    fun getRecommendations(objective: String) {
        viewModelScope.launch {
            aiState = AIState.Loading
            try {
                val prompt = """
                    Actúa como un nutricionista experto en español. 
                    Genera una recomendación de 3 comidas (Desayuno, Comida, Cena) para un usuario cuyo objetivo es: $objective.
                    Formato estricto (5 líneas por comida):
                    TIPO: [Desayuno/Comida/Cena]
                    NOMBRE: [Plato]
                    DESC: [Descripción corta]
                    KCAL: [Número] kcal
                    MACROS: P: [Número]g C: [Número]g
                """.trimIndent()

                val response = generativeModel.generateContent(prompt)
                val responseText = response.text ?: ""
                val meals = parseAiResponse(responseText)
                
                if (meals.isNotEmpty()) {
                    aiState = AIState.Success(meals)
                } else {
                    aiState = AIState.Error("La IA respondió pero el formato no es válido.")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                val errorMsg = e.localizedMessage ?: e.message ?: ""
                
                // Si ves este mensaje en la app, es que Gradle SIGUE usando la versión 0.9.0
                if (errorMsg.contains("MissingFieldException") || errorMsg.contains("details")) {
                    aiState = AIState.Error("ERROR DE VERSIÓN: El proyecto sigue usando el SDK 0.9.0 obsoleto. Por favor, haz un 'Rebuild Project' y asegúrate de estar conectado a internet.")
                } else {
                    val detailedError = when {
                        errorMsg.contains("404") -> "Error 404: El modelo no se encuentra. Verifica que tu API Key sea de AI Studio (aistudio.google.com)."
                        errorMsg.contains("User location") -> "Gemini no está disponible en tu país o región actual."
                        errorMsg.contains("API_KEY_INVALID") -> "La API Key no es válida."
                        else -> "Error: $errorMsg"
                    }
                    aiState = AIState.Error("Detalle: $detailedError")
                }
            }
        }
    }

    private fun parseAiResponse(text: String): List<RecommendedMeal> {
        val meals = mutableListOf<RecommendedMeal>()
        val blocks = text.split("\n\n").filter { it.isNotBlank() }
        for (block in blocks) {
            val lines = block.lines().filter { it.isNotBlank() }
            if (lines.size >= 5) {
                val type = lines[0].substringAfter("TIPO:").trim()
                val name = lines[1].substringAfter("NOMBRE:").trim()
                val desc = lines[2].substringAfter("DESC:").trim()
                val kcal = lines[3].substringAfter("KCAL:").trim()
                val macros = lines[4].substringAfter("MACROS:").trim()
                meals.add(RecommendedMeal(type.uppercase(), name, desc, kcal, macros))
            }
        }
        return meals
    }
}
