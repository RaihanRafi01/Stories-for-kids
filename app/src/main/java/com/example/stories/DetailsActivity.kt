package com.example.stories

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.stories.databinding.ActivityDetailsBinding
import com.example.stories.databinding.ActivityMainBinding

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var textSize = 20f

        val storyTitle = intent.getStringExtra("StoryTitle")
        val storyContent = intent.getStringExtra("StoryContent")

        binding.txtContent.text = storyContent
        binding.txtDetailsTitle.text = storyTitle

        binding.iconPlus.setOnClickListener {
            textSize += 2f
            binding.txtContent.setTextSize(textSize)
        }
        binding.iconMinus.setOnClickListener {
            textSize -= 2f
            binding.txtContent.setTextSize(textSize)
        }

    }
}