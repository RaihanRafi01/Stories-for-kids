package com.example.stories

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.stories.databinding.ActivityMainBinding
import com.example.stories.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animation = AnimationUtils.loadAnimation(this, R.anim.logo_anim)
        binding.imageView.startAnimation(animation)

        Handler().postDelayed({
            val iMain = Intent(this,MainActivity::class.java)
            startActivity(iMain)
            finish()
        },2000)
    }
}