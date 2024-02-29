package com.example.stories.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Bookmark::class],
    version = 1,
    exportSchema = true
)
abstract class StoryDatabase : RoomDatabase() {
    abstract fun getbookmarkDao(): BookmarkDao
    companion object{
        @Volatile
        private var INSTANCE : StoryDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE ?:
        synchronized(LOCK){
            INSTANCE ?:
            createDatabase(context).also{
                INSTANCE = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                StoryDatabase::class.java,
                "storydatabase"
            ).build()

    }
}