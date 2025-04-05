package com.example.studyonline.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.studyonline.GroupDetailActivity
import com.example.studyonline.R
import com.example.studyonline.models.Group
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class GroupAdapter(
    private val context: Context,
    private var groupList: List<Group>,
    private val showJoinButton: Boolean = true // true for JoinGroup screen, false for YourGroups
) : RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {

    inner class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvGroupName: TextView = itemView.findViewById(R.id.tvGroupName)
        val tvGroupCategory: TextView = itemView.findViewById(R.id.tvGroupCategory)
        val tvGroupDescription: TextView = itemView.findViewById(R.id.tvGroupDescription)
        val btnJoinGroup: Button = itemView.findViewById(R.id.btnJoinGroup)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_group, parent, false)
        return GroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val group = groupList[position]
        holder.tvGroupName.text = group.groupName
        holder.tvGroupCategory.text = group.category
        holder.tvGroupDescription.text = group.description

        if (showJoinButton) {
            // Show Join button functionality
            holder.btnJoinGroup.visibility = View.VISIBLE
            holder.btnJoinGroup.setOnClickListener {
                val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@setOnClickListener
                val firestore = FirebaseFirestore.getInstance()

                firestore.collection("StudyGroups")
                    .document(group.groupId)
                    .update("members", FieldValue.arrayUnion(userId))
                    .addOnSuccessListener {
                        Toast.makeText(context, "Joined ${group.groupName}", Toast.LENGTH_SHORT).show()
                        holder.btnJoinGroup.isEnabled = false
                        holder.btnJoinGroup.text = "Joined"
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context, "Failed to join: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        } else {
            // Hide join button and open group detail on click
            holder.btnJoinGroup.visibility = View.GONE
            holder.itemView.setOnClickListener {
                val intent = Intent(context, GroupDetailActivity::class.java)
                intent.putExtra("groupId", group.groupId)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = groupList.size

    fun updateData(newList: List<Group>) {
        groupList = newList
        notifyDataSetChanged()
    }
}
