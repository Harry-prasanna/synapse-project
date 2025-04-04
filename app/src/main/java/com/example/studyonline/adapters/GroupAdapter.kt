package com.example.studyonline.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.studyonline.R
import com.example.studyonline.models.Group

class GroupAdapter(
    private val context: Context,
    private var groupList: List<Group>
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

        holder.btnJoinGroup.setOnClickListener {
            // Simulate joining the group for now
            Toast.makeText(context, "Joined ${group.groupName}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = groupList.size

    // Optional: to update the list from outside
    fun updateData(newList: List<Group>) {
        groupList = newList
        notifyDataSetChanged()
    }
}
