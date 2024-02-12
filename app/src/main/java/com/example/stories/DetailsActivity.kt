package com.example.stories

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.res.colorResource
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.Lottie
import com.example.stories.databinding.ActivityDetailsBinding
import com.example.stories.databinding.ActivityMainBinding

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    @SuppressLint("ResourceAsColor")
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
        var check = false
        binding.btnTheme.setOnClickListener {
            if(check){
                // Night Mode
                binding.btnTheme.resumeAnimation()
                binding.detailsCardView.setCardBackgroundColor(R.color.detailsNight)
                check = false
            } else{
                // Day Mode
                binding.btnTheme.playAnimation()
                binding.btnTheme.setProgress(0.5f)
                binding.btnTheme.pauseAnimation()
                binding.detailsCardView.setCardBackgroundColor(R.color.detailsDay)
                check = true
            }

        }

    }
}