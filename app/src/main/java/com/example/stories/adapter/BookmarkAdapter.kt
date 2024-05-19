package com.example.stories.adapter

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stories.DetailsActivity
import com.example.stories.R
import com.example.stories.databinding.StoryTitleBinding
import java.util.Locale

class BookmarkAdapter (private var titles :Array<String>, private var contents :Array<String>, private val activity: Activity) : RecyclerView.Adapter<BookmarkAdapter.TitleViewHolder>() {
    private var newColor: Int = Color.parseColor("#1C5257")
    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
        val title = titles[position]
        val content = contents[position]
        holder.bind(title)
        val cardBackground = holder.itemView.findViewById<View>(R.id.storyTitleCard)
        cardBackground.setBackgroundColor(this.newColor)

        holder.itemView.setOnClickListener {
            val iDetails = Intent(activity, DetailsActivity::class.java)
            iDetails.putExtra("StoryTitle",title)
            iDetails.putExtra("StoryContent",content)
            iDetails.putExtra("Bookmarked",true)
            activity.startActivity(iDetails)
        }
    }
    class TitleViewHolder(private val binding: StoryTitleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(title: String) {
            binding.txtTitle.text = title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
        return TitleViewHolder(StoryTitleBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    fun setCardBackgroundColor(color: Int) {
        this.newColor = color // Store the color in the adapter
        notifyItemRangeChanged(0, itemCount) // Notify the adapter about the change
    }
    fun filterItems(query: String, previousTitles: Array<String>) {
        val filteredTitles = mutableListOf<String>()
        clearFilter(previousTitles)
        for (title in titles) {
            if (title.lowercase(Locale.getDefault()).contains(query.lowercase(Locale.getDefault()))) {
                filteredTitles.add(title)
            }
        }
        // Update adapter data and notify RecyclerView
        if (filteredTitles.isNotEmpty()) {
            titles = filteredTitles.toTypedArray()
            notifyDataSetChanged()
        }
    }

    fun clearFilter(previousTitles: Array<String>) {
        this.titles = previousTitles // Reset titles to original array
        notifyDataSetChanged()
    }

    fun updateItems(newTitles: Array<String>, newContent: Array<String>) {
        this.titles = newTitles
        this.contents = newContent
        notifyDataSetChanged() // Notify the RecyclerView about data changes
    }


}