package com.example.stories.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stories.DetailsActivity
import com.example.stories.databinding.StoryTitleBinding

class ItemAdapter (private val titles :Array<String>,private val contents :Array<String>,  private val requireContext: Context) : RecyclerView.Adapter<ItemAdapter.TitleViewHolder>() {

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
            val iDetails = Intent(requireContext, DetailsActivity::class.java)
            iDetails.putExtra("StoryTitle",title)
            iDetails.putExtra("StoryContent",content)
            requireContext.startActivity(iDetails)
        }
    }

    class TitleViewHolder(private val binding: StoryTitleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(title: String) {
            binding.txtTitle.text = title
        }
    }

}