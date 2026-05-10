package com.example.myapplication.repository

import com.example.myapplication.model.Meal
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class MealRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private val userId get() = auth.currentUser?.uid

    suspend fun addMeal(name: String, calories: Int, quantity: Int, unit: String): Result<Unit> {
        val uid = userId ?: return Result.failure(Exception("Usuario no autenticado"))
        return try {
            val mealRef = db.collection("users").document(uid).collection("meals").document()
            val meal = Meal(
                id = mealRef.id,
                userId = uid,
                name = name,
                calories = calories,
                quantity = quantity,
                unit = unit
            )
            mealRef.set(meal).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMeals(): Result<List<Meal>> {
        val uid = userId ?: return Result.failure(Exception("Usuario no autenticado"))
        return try {
            val snapshot = db.collection("users").document(uid).collection("meals")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get().await()
            val meals = snapshot.toObjects(Meal::class.java)
            Result.success(meals)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
