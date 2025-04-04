package com.example.studyonline

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studyonline.adapters.GroupAdapter
import com.example.studyonline.models.Group
import com.google.firebase.firestore.FirebaseFirestore

class JoinGroupActivity : AppCompatActivity() {

    private lateinit var recyclerViewGroups: RecyclerView
    private lateinit var groupAdapter: GroupAdapter
    private lateinit var firestore: FirebaseFirestore
    private val groupList = mutableListOf<Group>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_group)

        recyclerViewGroups = findViewById(R.id.recyclerViewGroups)
        recyclerViewGroups.layoutManager = LinearLayoutManager(this)

        firestore = FirebaseFirestore.getInstance()
        groupAdapter = GroupAdapter(this, groupList)
        recyclerViewGroups.adapter = groupAdapter

        fetchPublicGroups()
    }

    private fun fetchPublicGroups() {
        firestore.collection("StudyGroups")
            .whereEqualTo("type", "Public")
            .get()
            .addOnSuccessListener { documents ->
                groupList.clear()
                for (doc in documents) {
                    val group = Group(
                        groupId = doc.id,
                        groupName = doc.getString("groupName") ?: "",
                        description = doc.getString("description") ?: "",
                        category = doc.getString("category") ?: "",
                        type = doc.getString("type") ?: "",
                        createdBy = doc.getString("createdBy") ?: "",
                        timestamp = doc.getLong("timestamp") ?: 0L
                    )
                    groupList.add(group)
                }
                groupAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this@JoinGroupActivity,
                    "Error fetching groups: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }
}
