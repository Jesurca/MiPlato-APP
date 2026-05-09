package com.example.myapplication.repository

import com.example.myapplication.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    val currentUser get() = auth.currentUser

    suspend fun registerUser(name: String, email: String, pass: String, objective: String): Result<Unit> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, pass).await()
            val userId = result.user?.uid ?: throw Exception("ID de usuario nulo")
            
            val user = User(uid = userId, name = name, email = email, objective = objective)
            db.collection("users").document(userId).set(user).await()
            
            auth.signOut()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun loginUser(email: String, pass: String): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, pass).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() = auth.signOut()

    suspend fun getUserData(): User? {
        val userId = auth.currentUser?.uid ?: return null
        return try {
            val document = db.collection("users").document(userId).get().await()
            document.toObject(User::class.java)
        } catch (e: Exception) {
            null
        }
    }
}
