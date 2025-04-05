package com.example.studyonline.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.studyonline.R
import com.example.studyonline.models.OnboardingItem

class OnboardingAdapter(private val onboardingItems: List<OnboardingItem>) :
    RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

    class OnboardingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val titleText: TextView = view.findViewById(R.id.titleText)
        val descriptionText: TextView = view.findViewById(R.id.descriptionText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_onboarding, parent, false)
        return OnboardingViewHolder(view)
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        val item = onboardingItems[position]
        holder.imageView.setImageResource(item.imageResId)
        holder.titleText.text = item.title
        holder.descriptionText.text = item.description
        
        holder.titleText.setTextColor(
            ContextCompat.getColor(holder.itemView.context, R.color.purple_200)
        )
        holder.descriptionText.setTextColor(
            ContextCompat.getColor(holder.itemView.context, R.color.black)
        )
    }

    override fun getItemCount(): Int = onboardingItems.size
}
