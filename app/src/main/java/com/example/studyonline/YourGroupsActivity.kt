package com.example.studyonline

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studyonline.adapters.GroupAdapter
import com.example.studyonline.models.Group
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class YourGroupsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var groupAdapter: GroupAdapter
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private val yourGroupsList = mutableListOf<Group>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_your_groups)

        recyclerView = findViewById(R.id.recyclerViewYourGroups)
        recyclerView.layoutManager = LinearLayoutManager(this)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        groupAdapter = GroupAdapter(this, yourGroupsList, showJoinButton = false)
        recyclerView.adapter = groupAdapter

        fetchYourGroups()
    }

    private fun fetchYourGroups() {
        val userId = auth.currentUser?.uid ?: return

        firestore.collection("StudyGroups")
            .whereArrayContains("members", userId)
            .get()
            .addOnSuccessListener { result ->
                yourGroupsList.clear()
                for (doc in result) {
                    val group = Group(
                        groupId = doc.id,
                        groupName = doc.getString("groupName") ?: "",
                        description = doc.getString("description") ?: "",
                        category = doc.getString("category") ?: "",
                        type = doc.getString("type") ?: "",
                        createdBy = doc.getString("createdBy") ?: "",
                        timestamp = doc.getLong("timestamp") ?: 0L
                    )
                    yourGroupsList.add(group)
                }
                groupAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to fetch groups", Toast.LENGTH_SHORT).show()
            }
    }
}
