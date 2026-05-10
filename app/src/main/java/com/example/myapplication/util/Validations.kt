package com.example.myapplication.util

data class ValidationResult(
    val isValid: Boolean,
    val message: String? = null
)

object Validations {
    fun isValidEmail(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(false, "El correo no puede estar vacío.")
        }
        
        // Validación básica de formato
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        if (!email.matches(emailRegex)) {
            return ValidationResult(false, "El formato del correo electrónico no es válido.")
        }

        // Validación de correo corporativo (Opcional - Comentada para permitir cualquier correo)
        /*
        if (!email.endsWith("@test.com")) {
            return ValidationResult(false, "Ese correo no es corporativo (@test.com).")
        }
        */
        
        return ValidationResult(true)
    }

    fun isValidPassword(password: String): ValidationResult {
        if (password.length < 6) {
            return ValidationResult(false, "La contraseña debe tener al menos 6 caracteres.")
        }
        return ValidationResult(true)
    }

    fun validateRegister(
        name: String, 
        email: String, 
        password: String, 
        confirmPassword: String, 
        acceptedTerms: Boolean
    ): ValidationResult {
        if (name.isBlank()) return ValidationResult(false, "El nombre no puede estar vacío.")
        
        val emailRes = isValidEmail(email)
        if (!emailRes.isValid) return emailRes
        
        val passwordRes = isValidPassword(password)
        if (!passwordRes.isValid) return passwordRes
        
        if (password != confirmPassword) {
            return ValidationResult(false, "Las contraseñas no coinciden.")
        }
        
        if (!acceptedTerms) {
            return ValidationResult(false, "Debes aceptar los términos y condiciones.")
        }
        
        return ValidationResult(true)
    }
}
