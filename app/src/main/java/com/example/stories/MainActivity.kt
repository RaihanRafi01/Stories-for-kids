package com.example.stories

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stories.adapter.ItemAdapter
import com.example.stories.databinding.ActivityMainBinding

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //val titles = listOf("title 1","title 2","title 3","title 4","title 5","title 6","title 7","title 8","title 9","title 10","title 11","title 12","title 13","title 14","title 15","title 16","title 17","title 18","title 19","title 20")
        val storyTitles = resources.getStringArray(R.array.storyTitles)
        val storyContent = resources.getStringArray(R.array.storyContents)
        binding.recylerViewStoryTitle.layoutManager = LinearLayoutManager(this)
        binding.recylerViewStoryTitle.adapter = ItemAdapter(storyTitles,storyContent,this)


    }
}



