package com.example.stories.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stories.DetailsActivity
import com.example.stories.databinding.StoryTitleBinding
import java.util.Locale


class ItemAdapter (private var titles :Array<String>, private val contents :Array<String>, private val activity: Activity) : RecyclerView.Adapter<ItemAdapter.TitleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
        return TitleViewHolder(StoryTitleBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun getItemCount(): Int {
        return titles.size
    }

    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
        val title = titles[position]
        val content = contents[position]
        holder.bind(title)
        holder.itemView.setOnClickListener {
            val iDetails = Intent(activity, DetailsActivity::class.java)
            iDetails.putExtra("StoryTitle",title)
            iDetails.putExtra("StoryContent",content)
            activity.startActivity(iDetails)
        }
    }

    fun filterItems(query: String) {
        val filteredTitles = mutableListOf<String>()

        for (title in titles) {
            if (title.lowercase(Locale.getDefault()).contains(query.lowercase(Locale.getDefault()))) {
                filteredTitles.add(title)
            }
        }

        // Update adapter data and notify RecyclerView
        if (filteredTitles.isNotEmpty()) {
            titles = filteredTitles.toTypedArray()
            notifyDataSetChanged()
        } else {
            notifyDataSetChanged()
        }
    }

    class TitleViewHolder(private val binding: StoryTitleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(title: String) {
            binding.txtTitle.text = title
        }
    }

}