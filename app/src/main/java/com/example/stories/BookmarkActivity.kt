package com.example.stories

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stories.adapter.BookmarkAdapter
import com.example.stories.databinding.ActivityBookmarkBinding

class BookmarkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookmarkBinding
    private lateinit var toggle : ActionBarDrawerToggle
    private lateinit var itemAdapter: BookmarkAdapter
    private lateinit var bookmarkedTitles : List<Story>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)


        toggle = ActionBarDrawerToggle(this,binding.drawerLayoutBookmark,R.string.open,R.string.close)
        toggle.drawerArrowDrawable.color = getColor(R.color.white)
        binding.drawerLayoutBookmark.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        binding.recylerViewStoryTitleBookmark.layoutManager = LinearLayoutManager(this)
        val bookmarked = retrieveBookmarkedTitles()
        bookmarkedTitles = retrieveBookmarkedTitles()
        itemAdapter = BookmarkAdapter(bookmarked, this)
        binding.recylerViewStoryTitleBookmark.layoutManager = LinearLayoutManager(this)
        binding.recylerViewStoryTitleBookmark.adapter = itemAdapter

        val testLink = "https://play.google.com/store/apps/details?id=com.facebook.katana"
        val appLink = "https://play.google.com/store/apps/details?id="+packageName

        binding.navViewBookmark.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    val mainActivityIntent = Intent(this,MainActivity::class.java)
                    startActivity(mainActivityIntent)
                }
                R.id.nav_info -> {
                    val aboutDialog = Dialog(this)
                    aboutDialog.setContentView(R.layout.about_app)
                    aboutDialog.show()
                }
                R.id.nav_rate -> {
                    val rateIntent = Intent(Intent.ACTION_VIEW)
                    try {
                        rateIntent.setData(Uri.parse(appLink))
                        startActivity(rateIntent)
                    }catch (e : ActivityNotFoundException){
                        rateIntent.setData(Uri.parse(testLink))
                        startActivity(rateIntent)
                    }
                }
                R.id.nav_share -> {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_TEXT,appLink)
                    startActivity(Intent.createChooser(shareIntent,"Share This App"))
                }
                R.id.nav_bookmark -> {
                    binding.drawerLayoutBookmark.closeDrawers()
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
                    itemAdapter.filterItems(query,bookmarkedTitles)
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    itemAdapter.filterItems(newText,bookmarkedTitles)
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

    ////// new book mark logic

    private fun retrieveBookmarkedTitles(): List<Story> {
        val sharedPreferences = getSharedPreferences(getString(R.string.bookmark_pref_key), Context.MODE_PRIVATE)
        val bookmarkedKeys = sharedPreferences.all.keys

        // Filter keys to only include those that indicate bookmarked stories (identified by boolean values)
        val bookmarkedTitles = bookmarkedKeys.filter { it.startsWith("bookmark_") && sharedPreferences.getBoolean(it, false) }
            .map { it.removePrefix("bookmark_") } // Remove the prefix to get the original title

        // Retrieve additional details (content and imageUrl) for each bookmarked story
        val stories = bookmarkedTitles.mapNotNull { title ->
            val storedTitle = sharedPreferences.getString("title_$title", null)
            val content = sharedPreferences.getString("content_$title", null)
            val imageUrl = sharedPreferences.getString("imageUrl_$title", null)

            if (storedTitle != null && content != null && imageUrl != null) {
                Story(storedTitle, content, imageUrl)
            } else {
                null
            }
        }

        Log.e("Bookmarked Titles", stories.toString())
        return stories
    }

}