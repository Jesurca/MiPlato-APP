package com.example.myapplication.model

data class RecommendedMeal(
    val type: String, // DESAYUNO, COMIDA, CENA
    val name: String,
    val description: String,
    val calories: String,
    val macros: String
)
