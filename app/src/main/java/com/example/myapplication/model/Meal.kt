package com.example.myapplication.model

data class Meal(
    val id: String = "",
    val userId: String = "",
    val name: String = "",
    val calories: Int = 0,
    val proteins: Double = 0.0,
    val carbs: Double = 0.0,
    val fats: Double = 0.0,
    val quantity: Int = 0,
    val unit: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
