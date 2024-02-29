package com.example.stories.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class StoryViewModel(app : Application,private val storyRepository: StoryRepository) : AndroidViewModel(app) {

    fun addBookmark(bookmark: Bookmark) =
        viewModelScope.launch {
            storyRepository.insertBookmark(bookmark)
        }
    fun getAllBookmark() = storyRepository.getAllBookmark()

    fun deleteBookmark(bookmark: Bookmark) =
        viewModelScope.launch {
            storyRepository.deleteBookmark(bookmark)
        }

}