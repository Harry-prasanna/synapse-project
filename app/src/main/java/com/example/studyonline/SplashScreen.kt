package com.example.studyonline

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences: SharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val isFirstTime = sharedPreferences.getBoolean("FirstTimeLaunch", true)
        val isUserLoggedIn = sharedPreferences.getBoolean("IsLoggedIn", false)


        Handler(Looper.getMainLooper()).postDelayed({
            when {
                isFirstTime -> {
                    sharedPreferences.edit().putBoolean("FirstTimeLaunch", false).apply()
                    startActivity(Intent(this, OnboardingActivity::class.java))
                }
                isUserLoggedIn -> {
                    startActivity(Intent(this, MainActivity::class.java))
                }
                else -> {
                    startActivity(Intent(this, SignInActivity::class.java))
                }
            }
            finish() // Close Splash Screen
        }, 2000) // 2 seconds delay
    }
}
