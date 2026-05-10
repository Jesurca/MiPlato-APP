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
