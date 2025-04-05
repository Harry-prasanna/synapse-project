package com.example.studyonline

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studyonline.adapters.MessageAdapter
import com.example.studyonline.models.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import java.util.*
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage


class GroupDetailActivity : AppCompatActivity() {

    private lateinit var tvGroupName: TextView
    private lateinit var tvGroupCategory: TextView
    private lateinit var tvGroupDescription: TextView
    private lateinit var etMessage: EditText
    private lateinit var btnSend: ImageButton
    private lateinit var recyclerViewMessages: RecyclerView

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private lateinit var groupId: String
    private val messageList = mutableListOf<Message>()
    private lateinit var adapter: MessageAdapter

    private val PICK_FILE_REQUEST = 101
    private lateinit var storage: FirebaseStorage


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_detail)

        // Views
        tvGroupName = findViewById(R.id.tvGroupName)
        tvGroupCategory = findViewById(R.id.tvGroupCategory)
        tvGroupDescription = findViewById(R.id.tvGroupDescription)
        etMessage = findViewById(R.id.etMessage)
        btnSend = findViewById(R.id.btnSend)
        recyclerViewMessages = findViewById(R.id.recyclerViewMessages)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        groupId = intent.getStringExtra("groupId") ?: return

        recyclerViewMessages.layoutManager = LinearLayoutManager(this)
        adapter = MessageAdapter(messageList)
        recyclerViewMessages.adapter = adapter

        loadGroupDetails()
        listenForMessages()

        btnSend.setOnClickListener {
            val msg = etMessage.text.toString().trim()
            if (msg.isNotEmpty()) {
                sendMessage(msg)
            }
        }
        storage = FirebaseStorage.getInstance()

        findViewById<ImageButton>(R.id.btnUploadResource).setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "*/*"
            startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_FILE_REQUEST)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_FILE_REQUEST && resultCode == Activity.RESULT_OK) {
            val fileUri = data?.data
            if (fileUri != null) {
                uploadResourceToFirebase(fileUri)
            }
        }
    }

    private fun uploadResourceToFirebase(fileUri: Uri) {
        val fileName = "resource_${System.currentTimeMillis()}"
        val storageRef = storage.reference.child("StudyGroups/$groupId/Resources/$fileName")

        val uploadTask = storageRef.putFile(fileUri)
        uploadTask
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                    saveResourceToFirestore(fileName, downloadUrl.toString())
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Upload failed: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveResourceToFirestore(fileName: String, fileUrl: String) {
        val resource = mapOf(
            "fileName" to fileName,
            "fileUrl" to fileUrl,
            "uploadedBy" to (auth.currentUser?.displayName ?: "Unknown"),
            "timestamp" to System.currentTimeMillis()
        )

        firestore.collection("StudyGroups")
            .document(groupId)
            .collection("Resources")
            .add(resource)
            .addOnSuccessListener {
                Toast.makeText(this, "Resource uploaded", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to save resource", Toast.LENGTH_SHORT).show()
            }
    }


    private fun loadGroupDetails() {
        firestore.collection("StudyGroups").document(groupId)
            .get()
            .addOnSuccessListener { doc ->
                tvGroupName.text = doc.getString("groupName")
                tvGroupCategory.text = doc.getString("category")
                tvGroupDescription.text = doc.getString("description")
            }
    }

    private fun listenForMessages() {
        firestore.collection("StudyGroups")
            .document(groupId)
            .collection("Messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshots, _ ->
                if (snapshots != null) {
                    messageList.clear()
                    for (doc in snapshots) {
                        val message = doc.toObject(Message::class.java)
                        messageList.add(message)
                    }
                    adapter.notifyDataSetChanged()
                    recyclerViewMessages.scrollToPosition(messageList.size - 1)
                }
            }
    }

    private fun sendMessage(msg: String) {
        val user = auth.currentUser ?: return
        val message = Message(
            senderId = user.uid,
            senderName = user.displayName ?: "Anonymous",
            message = msg,
            timestamp = System.currentTimeMillis()
        )

        firestore.collection("StudyGroups")
            .document(groupId)
            .collection("Messages")
            .add(message)
            .addOnSuccessListener {
                etMessage.text.clear()
            }
    }
}
