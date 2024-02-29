package com.example.stories.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface BookmarkDao {
        @Upsert
        suspend fun insertBookmark(bookmark: Bookmark)
        @Query("SELECT * FROM bookmarks")
        fun getAllBookmarks(): LiveData<List<Bookmark>>
        @Delete
        suspend fun deleteBookmark(bookmark: Bookmark)

    }
