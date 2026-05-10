package com.example.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Meal
import com.example.myapplication.repository.MealRepository
import kotlinx.coroutines.launch

sealed class MealState {
    object Idle : MealState()
    object Loading : MealState()
    data class Success(val meals: List<Meal>) : MealState()
    data class Error(val message: String) : MealState()
}

class MealViewModel(private val repository: MealRepository = MealRepository()) : ViewModel() {

    var mealState by mutableStateOf<MealState>(MealState.Idle)
        private set

    fun addMeal(name: String, calories: Int, quantity: Int, unit: String) {
        viewModelScope.launch {
            mealState = MealState.Loading
            repository.addMeal(name, calories, quantity, unit).fold(
                onSuccess = {
                    fetchMeals()
                },
                onFailure = {
                    mealState = MealState.Error(it.message ?: "Error al agregar alimento")
                }
            )
        }
    }

    fun addRecommendedMeal(meal: com.example.myapplication.model.RecommendedMeal) {
        viewModelScope.launch {
            // Extraer solo el número de las calorías (ej: "400 kcal" -> 400)
            val calValue = meal.calories.replace(Regex("[^0-9]"), "").toIntOrNull() ?: 0
            
            repository.addMeal(
                name = meal.name,
                calories = calValue,
                quantity = 1,
                unit = "Servicio"
            ).fold(
                onSuccess = {
                    // Podríamos disparar un estado de "Agregado con éxito"
                },
                onFailure = {
                    mealState = MealState.Error("Error al guardar recomendación: ${it.message}")
                }
            )
        }
    }

    fun fetchMeals() {
        viewModelScope.launch {
            mealState = MealState.Loading
            repository.getMeals().fold(
                onSuccess = {
                    mealState = MealState.Success(it)
                },
                onFailure = {
                    mealState = MealState.Error(it.message ?: "Error al obtener historial")
                }
            )
        }
    }
}
