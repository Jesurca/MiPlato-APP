package com.example.myapplication

import android.app.Application
import com.google.firebase.FirebaseApp

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Firebase
        // If you don't use the google-services plugin, you must provide FirebaseOptions here.
        // For now, we assume the plugin is enabled and google-services.json is present.
        try {
            FirebaseApp.initializeApp(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
