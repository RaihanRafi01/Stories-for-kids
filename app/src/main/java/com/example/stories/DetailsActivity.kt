package com.example.stories

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

import com.bumptech.glide.Glide
import com.example.stories.database.Bookmark
import com.example.stories.databinding.ActivityDetailsBinding
import java.util.Locale

class DetailsActivity : AppCompatActivity(),TextToSpeech.OnInitListener {

    private lateinit var binding: ActivityDetailsBinding
    private var tts: TextToSpeech? = null
    private var etSpeak: String? = null
    private lateinit var storyTitle : String
    private lateinit var storyContent : String
    private lateinit var storyPicture : String
    private val sharedPreferences by lazy {
        val BOOKMARK_PREF_KEY = "501"
        getSharedPreferences(BOOKMARK_PREF_KEY, Context.MODE_PRIVATE)
    }
    private var isBookmarked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        //setupViewModel()
        textSizeChange()
        themeChange()

        binding.btnSpeak.setOnClickListener {
            speakOut()
        }
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        updateBookmarkIcon(isBookmarked)
        binding.bookmarkIcon.setOnClickListener {
            toggleBookmark()
        }

    }

    private fun init(){
        storyTitle = intent.getStringExtra("StoryTitle").toString()
        storyContent = intent.getStringExtra("StoryContent").toString()
        storyPicture = intent.getStringExtra("StoryImage").toString()
        isBookmarked = intent.getBooleanExtra("Bookmarked",false)

        binding.txtContent.text = storyContent
        binding.txtDetailsTitle.text = storyTitle
        Glide.with(binding.root.context)
            .load(storyPicture)
            .into(binding.imageView4)
        etSpeak = storyContent
        tts = TextToSpeech(this, this)
    }

    // Init ViewModel

    /*private fun setupViewModel(){
        val storyRepository = StoryRepository(StoryDatabase(this))
        val viewModelProviderFactory = BookmarkViewModelFactory(application,storyRepository)
        storyViewModel = ViewModelProvider(this,viewModelProviderFactory)[StoryViewModel::class.java]
    }*/

    // Text Size Change Feature
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

    // THEME FEATURE

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

    // VOICE FEATURE

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

    // BOOKMARK FEATURE

    private fun toggleBookmark() {
        val bookmark = Bookmark(storyTitle,storyContent,true)

        if (isBookmarked) {

            if (bookmark != null) {

                removeFromBookmarks(storyTitle)
                Log.d("Bookmark", "Bookmark deleted: ${bookmark.title}")
            }
            else {
                Log.w("Bookmark", "Cannot delete null bookmark")
            }

        } else {

            saveToBookmarks(storyTitle,storyContent,storyPicture)
            //storyViewModel.deleteBookmark(bookmark)

        }
        isBookmarked = !isBookmarked
        updateBookmarkIcon(isBookmarked)
    }
    private fun updateBookmarkIcon(isBookmarked: Boolean) {
        if (isBookmarked) {
            binding.bookmarkIcon.setImageResource(R.drawable.baseline_bookmark_24)
        } else {
            binding.bookmarkIcon.setImageResource(R.drawable.outline_bookmark_border_24)
        }
    }
    private fun saveToBookmarks(title: String, content: String, imageUrl: String) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("bookmark_$title", true) // Use a distinct key prefix for boolean
        editor.putString("title_$title", title)
        editor.putString("content_$title", content)
        editor.putString("imageUrl_$title", imageUrl)
        editor.apply()
        Log.d("Bookmark", "Bookmark saved: $title")
    }

    private fun removeFromBookmarks(title: String) {
        val editor = sharedPreferences.edit()
        editor.remove("bookmark_$title") // Remove the boolean flag
        editor.remove("title_$title") // Remove the title
        editor.remove("content_$title") // Remove the content
        editor.remove("imageUrl_$title") // Remove the imageUrl
        editor.apply()
        Log.d("Bookmark", "Bookmark removed: $title")
    }


}