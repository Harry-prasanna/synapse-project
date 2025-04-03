package com.example.studyonline

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.studyonline.adapters.OnboardingAdapter
import com.example.studyonline.models.OnboardingItem
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class OnboardingActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabIndicator: TabLayout
    private lateinit var nextButton: MaterialButton
    private lateinit var skipButton: TextView
    private lateinit var getStartedButton: MaterialButton

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        // Initialize Views
        viewPager = findViewById(R.id.viewPager)
        tabIndicator = findViewById(R.id.tabIndicator)
        nextButton = findViewById(R.id.btnNext)
        skipButton = findViewById(R.id.btnSkip)
        getStartedButton = findViewById(R.id.btnGetStarted)

        // Create Onboarding Screens
        val items = listOf(
            OnboardingItem(R.drawable.onboardingimage1,
                "Welcome to Study Groups",
                "Organize, manage, and collaborate with your study groups easily."
            ),
            OnboardingItem(R.drawable.onboardingimage2,
                "Create & Join Study Groups",
                "Connect with students, share resources, and discuss topics efficiently."
            ),
            OnboardingItem(R.drawable.onboardingimage3,
                "Schedule & Track Progress",
                "Plan study sessions, set reminders, and monitor your learning progress."
            ),
            OnboardingItem(R.drawable.onboardingimage4,
                "Virtual Study Sessions",
                "Join live video sessions, take notes, and enhance your learning experience."
            )
        )

        // Set up ViewPager Adapter
        val adapter = OnboardingAdapter(items)
        viewPager.adapter = adapter

        // Set up Dots Indicator
        TabLayoutMediator(tabIndicator, viewPager) { _, _ -> }.attach()

        // Show/Hide "Get Started" Button on Last Page
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == items.size - 1) {  // Last page
                    getStartedButton.visibility = View.VISIBLE
                    nextButton.visibility = View.GONE
                    skipButton.visibility = View.GONE
                } else {
                    getStartedButton.visibility = View.GONE
                    nextButton.visibility = View.VISIBLE
                    skipButton.visibility = View.VISIBLE
                }
            }
        })

        // Next Button Click
        nextButton.setOnClickListener {
            if (viewPager.currentItem < items.size - 1) {
                viewPager.currentItem += 1 // Move to next page
            }
        }

        // Skip Button Click
        skipButton.setOnClickListener {
            finishOnboarding() // Skip and go to SignInActivity
        }

        // Get Started Button Click
        getStartedButton.setOnClickListener {
            finishOnboarding() // Redirect to SignInActivity
        }
    }

    private fun finishOnboarding() {
        // Save to SharedPreferences (Mark Onboarding as Seen)
        val sharedPreferences: SharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("FirstTimeLaunch", false)
        editor.apply()

        
        startActivity(Intent(this, SignInActivity::class.java))
        finish() // Close OnboardingActivity
    }
}

