package com.example.stories

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stories.adapter.ItemAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.stories.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle : ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toggle = ActionBarDrawerToggle(this,binding.drawerLayout,R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
            }
            true
        }

        //val titles = listOf("title 1","title 2","title 3","title 4","title 5","title 6","title 7","title 8","title 9","title 10","title 11","title 12","title 13","title 14","title 15","title 16","title 17","title 18","title 19","title 20")
        val storyTitles = resources.getStringArray(R.array.storyTitles)
        val storyContent = resources.getStringArray(R.array.storyContents)
        binding.recylerViewStoryTitle.layoutManager = LinearLayoutManager(this)
        binding.recylerViewStoryTitle.adapter = ItemAdapter(storyTitles,storyContent,this)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true }
        return super.onOptionsItemSelected(item)
    }
}



