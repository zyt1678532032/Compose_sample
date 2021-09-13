package com.sues.noteapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "title")
    var title: String? = null,
    @ColumnInfo(name = "date_time")
    var dateTime: String,
    @ColumnInfo(name = "subtitle")
    var subTitle: String? = null,
    @ColumnInfo(name = "note_text")
    var noteText: String,
    @ColumnInfo(name = "image_path")
    var imagePath: String? = null,
    @ColumnInfo(name = "color")
    var color: String? = null,
    @ColumnInfo(name = "web_link")
    var webLink: String? = null,
)