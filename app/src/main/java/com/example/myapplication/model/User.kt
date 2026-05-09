package com.example.myapplication.model

data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val objective: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
