package com.example.stories

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stories.adapter.BookmarkAdapter
import com.example.stories.database.BookmarkViewModelFactory
import com.example.stories.database.StoryDatabase
import com.example.stories.database.StoryRepository
import com.example.stories.database.StoryViewModel
import com.example.stories.databinding.ActivityBookmarkBinding

class BookmarkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookmarkBinding
    private lateinit var toggle : ActionBarDrawerToggle
    private lateinit var title : Array<String>
    private lateinit var storyContent : Array<String>
    private lateinit var itemAdapter: BookmarkAdapter
    lateinit var storyViewModel: StoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()

        toggle = ActionBarDrawerToggle(this,binding.drawerLayoutBookmark,R.string.open,R.string.close)
        toggle.drawerArrowDrawable.color = getColor(R.color.white)
        binding.drawerLayoutBookmark.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title = resources.getStringArray(R.array.storyTitles)
        storyContent = resources.getStringArray(R.array.storyContents)

        storyViewModel.getAllBookmark().observe(this) { bookmarks ->
            bookmarks?.forEach { bookmark ->

                if (bookmarks != null) {
                    val titles = bookmarks.map { it.title }.toTypedArray()
                    val content = bookmarks.map { it.content }.toTypedArray()
                    itemAdapter.updateItems(titles, content)
                }
            }
        }

        binding.recylerViewStoryTitleBookmark.layoutManager = LinearLayoutManager(this)
        itemAdapter = BookmarkAdapter(title,storyContent,this)
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

    private fun setupViewModel(){
        val storyRepository = StoryRepository(StoryDatabase(this))
        val viewModelProviderFactory = BookmarkViewModelFactory(application,storyRepository)
        storyViewModel = ViewModelProvider(this,viewModelProviderFactory)[StoryViewModel::class.java]
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