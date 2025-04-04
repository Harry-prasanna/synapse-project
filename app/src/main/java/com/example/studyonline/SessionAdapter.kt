package com.example.studyonline

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studyonline.models.SessionItem


class SessionAdapter(private val sessions: List<SessionItem>) :
    RecyclerView.Adapter<SessionAdapter.SessionViewHolder>() {

    class SessionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.sessionTitle)
        val date: TextView = itemView.findViewById(R.id.sessionDate)
        val duration: TextView = itemView.findViewById(R.id.sessionDuration)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.session_item, parent, false)
        return SessionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        val session = sessions[position]
        holder.title.text = session.title
        holder.date.text = session.date
        holder.duration.text = session.duration
    }

    override fun getItemCount() = sessions.size
}

