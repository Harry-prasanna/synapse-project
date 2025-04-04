package com.example.studyonline.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studyonline.R
import com.example.studyonline.models.Session

class SessionAdapter(private val sessionList: List<Session>) :
    RecyclerView.Adapter<SessionAdapter.SessionViewHolder>() {

    class SessionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSessionTopic: TextView = itemView.findViewById(R.id.tvSessionTopic)
        val tvGroupName: TextView = itemView.findViewById(R.id.tvGroupName)
        val tvDateTime: TextView = itemView.findViewById(R.id.tvDateTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_session, parent, false)
        return SessionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        val session = sessionList[position]
        holder.tvSessionTopic.text = session.topic
        holder.tvGroupName.text = session.groupName
        holder.tvDateTime.text = session.dateTime
    }

    override fun getItemCount(): Int = sessionList.size
}
