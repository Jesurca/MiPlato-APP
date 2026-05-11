package com.example.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.RecommendedMeal
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

sealed class AIState {
    object Idle : AIState()
    object Loading : AIState()
    data class Success(val recommendations: List<RecommendedMeal>) : AIState()
    data class Error(val message: String) : AIState()
}

class AIViewModel : ViewModel() {

    // Requiere SDK 0.9.0 para gemini-1.5-flash
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
                    MACROS: P: [Número]g C: [Número]g G: [Número]g
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
                val errorMsg = e.localizedMessage ?: e.message ?: "Error desconocido"

                val detailedError = when {
                    errorMsg.contains("404") -> "Modelo no encontrado. Por favor, pulsa el botón de Sync (elefante) para activar el SDK 0.9.0."
                    errorMsg.contains("403") || errorMsg.contains("blocked") -> "La API Key está bloqueada. Revisa restricciones en Google AI Studio."
                    errorMsg.contains("API_KEY_INVALID") -> "La API Key no es válida."
                    else -> errorMsg
                }
                aiState = AIState.Error("Detalle: $detailedError")
            }
        }
    }

    private fun parseAiResponse(text: String): List<RecommendedMeal> {
        val meals = mutableListOf<RecommendedMeal>()
        val blocks = text.split(Regex("(?=TIPO:)")).filter { it.isNotBlank() }
        for (block in blocks) {
            val lines = block.lines().map { it.trim() }.filter { it.isNotBlank() }
            if (lines.size >= 5) {
                val type = lines.find { it.startsWith("TIPO:") }?.substringAfter(":")?.trim() ?: ""
                val name = lines.find { it.startsWith("NOMBRE:") }?.substringAfter(":")?.trim() ?: ""
                val desc = lines.find { it.startsWith("DESC:") }?.substringAfter(":")?.trim() ?: ""
                val kcal = lines.find { it.startsWith("KCAL:") }?.substringAfter(":")?.trim() ?: ""
                val macros = lines.find { it.startsWith("MACROS:") }?.substringAfter(":")?.trim() ?: ""
                
                if (name.isNotEmpty()) {
                    meals.add(RecommendedMeal(type.uppercase(), name, desc, kcal, macros))
                }
            }
        }
        return meals
    }
}
