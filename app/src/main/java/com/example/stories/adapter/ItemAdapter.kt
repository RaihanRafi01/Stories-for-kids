package com.example.stories.adapter
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.stories.DetailsActivity
import com.example.stories.R
import com.example.stories.Story
import com.example.stories.databinding.StoryTitleBinding
import java.util.Locale

class ItemAdapter(private var stories: List<Story>, private val activity: Activity,private var initialTitles: List<Story>) :
    RecyclerView.Adapter<ItemAdapter.TitleViewHolder>() {

    private var newColor: Int = Color.parseColor("#78BE33")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
        return TitleViewHolder(StoryTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = stories.size

    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
        val story = stories[position]
        holder.bind(story)

        val cardBackground = holder.itemView.findViewById<View>(R.id.storyTitleCard)
        cardBackground.setBackgroundColor(this.newColor)

        holder.itemView.setOnClickListener {
            val iDetails = Intent(activity, DetailsActivity::class.java).apply {
                putExtra("StoryTitle", story.title)
                putExtra("StoryContent", story.content)
                putExtra("StoryImage", story.imageUrl)
            }
            activity.startActivity(iDetails)
        }
    }

    class TitleViewHolder(private val binding: StoryTitleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(story: Story) {
            binding.txtTitle.text = story.title
            Glide.with(binding.root.context)
                .load(story.imageUrl)
                .into(binding.storyImg)
        }
    }

    fun setCardBackgroundColor(color: Int) {
        this.newColor = color
        notifyItemRangeChanged(0, itemCount)
    }

     fun filterItems(query: String) {
         stories = initialTitles
        val filteredStories = stories.filter {
            it.title.lowercase(Locale.getDefault()).contains(query.lowercase(Locale.getDefault()))
        }
        if (filteredStories.isNotEmpty()) {
            stories = filteredStories
            notifyDataSetChanged()
        }
    }
}
