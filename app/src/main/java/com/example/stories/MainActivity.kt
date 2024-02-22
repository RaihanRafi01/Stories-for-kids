package com.example.stories

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stories.adapter.ItemAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.stories.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle : ActionBarDrawerToggle
    private lateinit var title : Array<String>
    private lateinit var storyContent : Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toggle = ActionBarDrawerToggle(this,binding.drawerLayout,R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title = resources.getStringArray(R.array.storyTitles)
        storyContent = resources.getStringArray(R.array.storyContents)
        binding.recylerViewStoryTitle.layoutManager = LinearLayoutManager(this)
        binding.recylerViewStoryTitle.adapter = ItemAdapter(title,storyContent,this)

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_theme -> onThemeChange()
            }
            true
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true }
        when(item.itemId){
            R.id.nav_theme -> {
                //Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show()
                onThemeChange()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun onThemeChange() {
        findViewById<CardView>(R.id.storyTitleCard).setCardBackgroundColor(getColor(R.color.detailsnight))
    }
}



