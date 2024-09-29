package com.example.todoapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class splash_screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)


        // Check if this is the user's first time opening the app
        val sharedPref = getSharedPreferences("onboarding", MODE_PRIVATE)
        val isFirstTime = sharedPref.getBoolean("isFirstTime", true)

        // Use Handler to delay the transition for 3 seconds (3000 milliseconds)
        Handler(Looper.getMainLooper()).postDelayed({
            if (isFirstTime) {
                // If first time, show Onboarding screens
                startActivity(Intent(this, OnboardingActivity::class.java))

                // Save that the user has seen the onboarding screens
                val editor = sharedPref.edit()
                editor.putBoolean("isFirstTime", false)
                editor.apply()
            } else {
                // If not first time, go to Login screen
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish() // Close SplashActivity after the transition
        }, 3000) //


    }
}