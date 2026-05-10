package com.example.myapplication.model

data class Meal(
    val id: String = "",
    val userId: String = "",
    val name: String = "",
    val calories: Int = 0,
    val quantity: Int = 0,
    val unit: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
