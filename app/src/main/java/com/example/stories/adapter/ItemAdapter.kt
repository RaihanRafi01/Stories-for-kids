package com.example.stories.adapter
import android.app.Activity
import android.content.Context
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

class ItemAdapter (private var titles :Array<String>, private val contents :Array<String>, private val activity: Activity) : RecyclerView.Adapter<ItemAdapter.TitleViewHolder>() {
    private var newColor: Int = Color.parseColor("#1C5257")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
        return TitleViewHolder(StoryTitleBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    override fun getItemCount(): Int {
        return titles.size
    }
    private val sharedPref = activity.getSharedPreferences("bookmarks", Context.MODE_PRIVATE)
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
            activity.startActivity(iDetails)
        }
    }
    class TitleViewHolder(private val binding: StoryTitleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(title: String) {
            binding.txtTitle.text = title
        }
    }
    fun setCardBackgroundColor(color: Int) {
        this.newColor = color // Store the color in the adapter
        notifyItemRangeChanged(0, itemCount) // Notify the adapter about the change
    }

    fun filterItems(query: String) {
        val filteredTitles = mutableListOf<String>()
        clearFilter()
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

    fun clearFilter() {
        titles = activity.resources.getStringArray(R.array.storyTitles) // Reset titles to original array
        notifyDataSetChanged()
    }

}