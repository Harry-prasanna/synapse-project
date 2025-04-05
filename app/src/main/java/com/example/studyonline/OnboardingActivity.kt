package com.example.studyonline

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.studyonline.adapters.OnboardingAdapter
import com.example.studyonline.models.OnboardingItem
import com.google.android.material.button.MaterialButton

class OnboardingActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var dotsContainer: LinearLayout
    private lateinit var nextButton: MaterialButton
    private lateinit var skipButton: TextView
    private lateinit var getStartedButton: MaterialButton

    private lateinit var dots: Array<TextView?>
    private var currentPos = 0
    private lateinit var items: List<OnboardingItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        // Initialize views
        viewPager = findViewById(R.id.viewPager)
        dotsContainer = findViewById(R.id.dotsContainer)
        nextButton = findViewById(R.id.btnNext)
        skipButton = findViewById(R.id.skip_btn)
        getStartedButton = findViewById(R.id.btnGetStarted)

        // Onboarding screen data
        items = listOf(
            OnboardingItem(R.drawable.onboardingimage1,
                "Welcome to Study Groups",
                "Organize, manage, and collaborate with your study groups easily."),
            OnboardingItem(R.drawable.onboardingimage2,
                "Create & Join Study Groups",
                "Connect with students, share resources, and discuss topics efficiently."),
            OnboardingItem(R.drawable.onboardingimage3,
                "Schedule & Track Progress",
                "Plan study sessions, set reminders, and monitor your learning progress."),
            OnboardingItem(R.drawable.onboardingimage4,
                "Virtual Study Sessions",
                "Join live video sessions, take notes, and enhance your learning experience.")
        )

        // Set Adapter
        val adapter = OnboardingAdapter(items)
        viewPager.adapter = adapter

        // Setup dots
        addDots(0)

        // Page change listener
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                addDots(position)
                currentPos = position

                // Toggle button visibility
                if (position == items.size - 1) {
                    nextButton.visibility = View.GONE
                    skipButton.visibility = View.GONE
                    getStartedButton.visibility = View.VISIBLE
                } else {
                    nextButton.visibility = View.VISIBLE
                    skipButton.visibility = View.VISIBLE
                    getStartedButton.visibility = View.GONE
                }
            }
        })

        // Button Click Listeners
        nextButton.setOnClickListener {
            if (currentPos < items.size - 1) {
                viewPager.currentItem = currentPos + 1
            }
        }

        skipButton.setOnClickListener {
            finishOnboarding()
        }

        getStartedButton.setOnClickListener {
            finishOnboarding()
        }
    }

    private fun addDots(position: Int) {
        dots = arrayOfNulls(items.size)
        dotsContainer.removeAllViews()

        for (i in dots.indices) {
            dots[i] = TextView(this)
            dots[i]?.text = Html.fromHtml("&#8226;")
            dots[i]?.textSize = 35f
            dots[i]?.setTextColor(Color.LTGRAY)
            dotsContainer.addView(dots[i])
        }

        if (dots.isNotEmpty()) {
            dots[position]?.setTextColor(resources.getColor(R.color.purple_200))
        }
    }

    private fun finishOnboarding() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("FirstTimeLaunch", false)
        editor.apply()

        startActivity(Intent(this, SignInActivity::class.java))
        finish()
    }
}
