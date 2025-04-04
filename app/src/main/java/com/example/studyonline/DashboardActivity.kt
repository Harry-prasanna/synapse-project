package com.example.studyonline

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth

class DashboardActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var cardProgress: CardView
    private lateinit var cardCreateGroup: CardView
    private lateinit var cardJoinGroup: CardView
    private lateinit var cardUpcomingSessions: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Initialize FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        // Check if the user is not logged in, redirect to SignInActivity
        if (firebaseAuth.currentUser == null) {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }

        // Bind CardViews
        cardProgress = findViewById(R.id.cardProgress)
        cardCreateGroup = findViewById(R.id.cardCreateGroup)
        cardJoinGroup = findViewById(R.id.cardJoinGroup)
        cardUpcomingSessions = findViewById(R.id.cardUpcomingSessions)

        // Click: Study Progress
        cardProgress.setOnClickListener {
            Toast.makeText(this, "Opening Progress Screen...", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ProgressActivity::class.java))
        }

        // Click: Create Study Group
        cardCreateGroup.setOnClickListener {
            Toast.makeText(this, "Opening Create Group Screen...", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, CreateGroupActivity::class.java))
        }

        // Click: Join Study Group
        cardJoinGroup.setOnClickListener {
            Toast.makeText(this, "Opening Join Group Screen...", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, JoinGroupActivity::class.java))
        }

        // Click: Upcoming Sessions
        cardUpcomingSessions.setOnClickListener {
            Toast.makeText(this, "Opening Upcoming Sessions...", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, UpcomingSessionsActivity::class.java))
        }
    }
}
