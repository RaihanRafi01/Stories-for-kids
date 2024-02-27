package com.example.stories
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stories.adapter.ItemAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.stories.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle : ActionBarDrawerToggle
    private lateinit var title : Array<String>
    private lateinit var storyContent : Array<String>
    private lateinit var itemAdapter: ItemAdapter

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
        itemAdapter = ItemAdapter(title,storyContent,this)
        binding.recylerViewStoryTitle.adapter = itemAdapter

        val testLink = "com.facebook.katana"
        val appLink = "https://play.google.com/store/apps/details?id="

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> onThemeChange()
                R.id.nav_info -> {
                    val aboutDialog = Dialog(this)
                    aboutDialog.setContentView(R.layout.about_app)
                    aboutDialog.show()
                }
                R.id.nav_rate -> {
                    val rateIntent = Intent(Intent.ACTION_VIEW)
                    try {
                        rateIntent.setData(Uri.parse(appLink+packageName))
                        startActivity(rateIntent)
                    }catch (e : ActivityNotFoundException){
                        rateIntent.setData(Uri.parse(appLink+testLink))
                        startActivity(rateIntent)
                    }
                }
            }
            true
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)

        val searchItem = menu?.findItem(R.id.nav_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null) {
                    itemAdapter.filterItems(query)
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    itemAdapter.filterItems(newText)
                }
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true }
        when(item.itemId){
            R.id.nav_theme -> {
                onThemeChange()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private var isNightTheme = false
    private fun onThemeChange() {
        isNightTheme = !isNightTheme // Toggle theme state

        val newColor = if (isNightTheme) {
            getColor(R.color.detailsnight)
        } else {
            getColor(R.color.detailsday)
        }
        itemAdapter.setCardBackgroundColor(newColor)
    }
}



