package com.example.stories

import android.annotation.SuppressLint
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.stories.databinding.ActivityDetailsBinding
import java.util.Locale

class DetailsActivity : AppCompatActivity(),TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityDetailsBinding
    private var tts: TextToSpeech? = null
    private var etSpeak: String? = null
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val storyTitle = intent.getStringExtra("StoryTitle")
        val storyContent = intent.getStringExtra("StoryContent")

        binding.txtContent.text = storyContent
        binding.txtDetailsTitle.text = storyTitle
        etSpeak = storyContent
        tts = TextToSpeech(this, this)

        textSizeChange()
        themeChange()

        binding.btnSpeak.setOnClickListener {
            speakOut()
        }
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

    }
    private fun textSizeChange() {
        var textSize = 20f
        binding.iconPlus.setOnClickListener {
            textSize += 2f
            binding.txtContent.setTextSize(textSize)
        }
        binding.iconMinus.setOnClickListener {
            textSize -= 2f
            binding.txtContent.setTextSize(textSize)
        }
    }

    private fun themeChange(){
        var check = false
        binding.btnTheme.setOnClickListener {
            if(check){
                // Day Mode
                binding.btnTheme.resumeAnimation()
                binding.imageView3.alpha = 0.5f
                binding.imageView4.alpha = 0.5f
                binding.detailsCardView.setCardBackgroundColor(getColor(R.color.detailsday))
                check = false
            } else{
                // Night Mode
                binding.btnTheme.playAnimation()
                binding.btnTheme.setProgress(0.5f)
                binding.btnTheme.pauseAnimation()
                binding.imageView3.alpha = 0.2f
                binding.imageView4.alpha = 0.2f
                binding.detailsCardView.setCardBackgroundColor(getColor(R.color.detailsnight))
                check = true
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language not supported!")
            } else {
                binding.btnSpeak!!.isEnabled = true
            }
        }
    }
    private fun speakOut() {
        tts!!.speak(etSpeak, TextToSpeech.QUEUE_FLUSH, null,"")
    }
    public override fun onDestroy() {
        // Shutdown TTS when
        // activity is destroyed
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }
}