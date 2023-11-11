package com.sues.noteapp.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sues.noteapp.ui.theme.SelectedColor

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo var title: String? = null,
    @ColumnInfo var dateTime: String? = null,
    @ColumnInfo var noteText: String? = null,
    @ColumnInfo var imagePath: String? = null,
    @ColumnInfo var selectedColor: SelectedColor = SelectedColor.Color1,
)