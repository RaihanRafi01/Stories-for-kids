package com.example.stories.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.ParcelField
import kotlinx.parcelize.Parcelize

@Entity(tableName = "bookmarks")
@Parcelize
data class Bookmark(
    @PrimaryKey
    val title: String,
    val content: String,
    var bookmarked: Boolean
) : Parcelable

