package com.example.studyonline

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth

class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var cardProgress: CardView
    private lateinit var cardCreateGroup: CardView
    private lateinit var cardJoinGroup: CardView
    private lateinit var cardUpcomingSessions: CardView
    private lateinit var cardYourGroups: CardView


    // Navigation Drawer Variables
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var menuIcon: ImageView
    private lateinit var contentView: View

    private val END_SCALE = 0.7f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_dashboard)

        // Initialize FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        // Redirect if not logged in
        if (firebaseAuth.currentUser == null) {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }

        // Bind CardViews
        cardProgress = findViewById(R.id.cardProgress)
        cardCreateGroup = findViewById(R.id.cardCreateGroup)
        cardJoinGroup = findViewById(R.id.cardJoinGroup)
        cardUpcomingSessions = findViewById(R.id.cardUpcomingSessions)
        cardYourGroups = findViewById(R.id.cardYourGroups)

        // Card Clicks
        cardProgress.setOnClickListener {
            Toast.makeText(this, "Opening Progress Screen...", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ProgressActivity::class.java))
        }

        cardCreateGroup.setOnClickListener {
            Toast.makeText(this, "Opening Create Group Screen...", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, CreateGroupActivity::class.java))
        }

        cardJoinGroup.setOnClickListener {
            Toast.makeText(this, "Opening Join Group Screen...", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, JoinGroupActivity::class.java))
        }

        cardUpcomingSessions.setOnClickListener {
            Toast.makeText(this, "Opening Upcoming Sessions...", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, UpcomingSessionsActivity::class.java))
        }


        cardYourGroups.setOnClickListener {
            val intent = Intent(this, YourGroupsActivity::class.java)
            startActivity(intent)
        }


        // Navigation Drawer Setup
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)
        menuIcon = findViewById(R.id.menu_icon)
        contentView = findViewById(R.id.content)

        setupNavigationDrawer()
    }

    private fun setupNavigationDrawer() {
        navigationView.bringToFront()
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.setCheckedItem(R.id.nav_home)

        menuIcon.setOnClickListener {
            if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        animateNavigationDrawer()
    }

    private fun animateNavigationDrawer() {
        drawerLayout.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                val diffScaledOffset = slideOffset * (1 - END_SCALE)
                val offsetScale = 1 - diffScaledOffset
                contentView.scaleX = offsetScale
                contentView.scaleY = offsetScale

                val xOffset = drawerView.width * slideOffset
                val xOffsetDiff = contentView.width * diffScaledOffset / 2
                val xTranslation = xOffset - xOffsetDiff
                contentView.translationX = xTranslation
            }
        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle menu item clicks here
        when (item.itemId) {
            R.id.nav_home -> {
                // Already on home
            }
            R.id.nav_logout -> {
                firebaseAuth.signOut()
                Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, SignInActivity::class.java))
                finish()
            }
            // Add other navigation actions if needed
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
