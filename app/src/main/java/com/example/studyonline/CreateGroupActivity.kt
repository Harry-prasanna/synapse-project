package com.example.studyonline

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CreateGroupActivity : AppCompatActivity() {

    private lateinit var etGroupName: TextInputEditText
    private lateinit var etGroupDescription: TextInputEditText
    private lateinit var etGroupCategory: TextInputEditText
    private lateinit var rgGroupType: RadioGroup
    private lateinit var btnCreateGroup: MaterialButton

    private lateinit var firestore: FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_group)

        // Initialize Firebase
        firestore = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()

        // Bind views
        etGroupName = findViewById(R.id.etGroupName)
        etGroupDescription = findViewById(R.id.etGroupDescription)
        etGroupCategory = findViewById(R.id.etGroupCategory)
        rgGroupType = findViewById(R.id.rgGroupType)
        btnCreateGroup = findViewById(R.id.btnCreateGroup)

        // Handle Create Button Click
        btnCreateGroup.setOnClickListener {
            val groupName = etGroupName.text.toString().trim()
            val groupDesc = etGroupDescription.text.toString().trim()
            val groupCategory = etGroupCategory.text.toString().trim()
            val selectedTypeId = rgGroupType.checkedRadioButtonId

            if (groupName.isEmpty() || groupDesc.isEmpty() || groupCategory.isEmpty() || selectedTypeId == -1) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val groupType = findViewById<RadioButton>(selectedTypeId).text.toString()
            val userId = firebaseAuth.currentUser?.uid ?: "anonymous"

            val groupData = hashMapOf(
                "groupName" to groupName,
                "description" to groupDesc,
                "category" to groupCategory,
                "type" to groupType,
                "createdBy" to userId,
                "timestamp" to System.currentTimeMillis()
            )

            val collectionRef = firestore.collection("StudyGroups")

            collectionRef
                .add(groupData)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Group created successfully!", Toast.LENGTH_SHORT).show()

                        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                            finish()
                        }, 1200)

                    } else {
                        Toast.makeText(this, "Creation failed!", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }


    }
}
