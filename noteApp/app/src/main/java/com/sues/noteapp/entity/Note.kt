package com.sues.noteapp.entity

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sues.noteapp.ui.theme.SelectedColor
import java.io.Serializable

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int ? = null,
    @ColumnInfo(name = "title")
    var title: String? = null,
    @ColumnInfo(name = "date_time")
    var dateTime: String,
//    @ColumnInfo(name = "subtitle")
//    var subTitle: String? = null,
    @ColumnInfo(name = "note_text")
    var noteText: String,
    @ColumnInfo(name = "image_path")
    var imagePath: String? = null,
    @ColumnInfo(name = "color")
    var color: SelectedColor = SelectedColor.Color1,
    @ColumnInfo(name = "web_link")
    var webLink: String? = null,
): Serializable{
    constructor() : this(dateTime = "",noteText = "")
}