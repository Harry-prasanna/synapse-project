package com.example.studyonline

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studyonline.adapters.SessionAdapter
import com.example.studyonline.models.Session

class UpcomingSessionsActivity : AppCompatActivity() {

    private lateinit var recyclerViewSessions: RecyclerView
    private lateinit var sessionAdapter: SessionAdapter
    private lateinit var sessionList: ArrayList<Session>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcomingsessions)

        recyclerViewSessions = findViewById(R.id.recyclerViewSessions)
        recyclerViewSessions.layoutManager = LinearLayoutManager(this)
        sessionList = ArrayList()

        // Add dummy data
        sessionList.add(
            Session(
                sessionId = "1",
                topic = "Data Structures Q&A",
                groupName = "CS Study Group",
                dateTime = "April 7, 2025 - 6:00 PM",
                createdBy = "user123"
            )
        )

        sessionList.add(
            Session(
                sessionId = "2",
                topic = "Android Kotlin Basics",
                groupName = "Mobile Dev Group",
                dateTime = "April 8, 2025 - 4:30 PM",
                createdBy = "user456"
            )
        )

        sessionList.add(
            Session(
                sessionId = "3",
                topic = "DBMS Revision",
                groupName = "Database Enthusiasts",
                dateTime = "April 10, 2025 - 5:00 PM",
                createdBy = "user789"
            )
        )

        // Setup Adapter
        sessionAdapter = SessionAdapter(sessionList)
        recyclerViewSessions.adapter = sessionAdapter
    }
}
