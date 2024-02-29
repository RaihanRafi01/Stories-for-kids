package com.example.stories.database

import androidx.lifecycle.LiveData

class StoryRepository(private val db: StoryDatabase) {

    suspend fun insertBookmark(bookmark: Bookmark) = db.getbookmarkDao().insertBookmark(bookmark)
    suspend fun deleteBookmark(bookmark: Bookmark) = db.getbookmarkDao().deleteBookmark(bookmark)

    fun getAllBookmark(): LiveData<List<Bookmark>> {
        return db.getbookmarkDao().getAllBookmarks()
    }

}