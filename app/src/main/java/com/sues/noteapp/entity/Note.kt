package com.sues.noteapp.entity


import com.sues.noteapp.ui.theme.SelectedColor

data class Note(
    var id: Int? = 1,
    var title: String? = null,
    var dateTime: String,
    var noteText: String,
    var imagePath: String? = null,
    var selectedColor: SelectedColor = SelectedColor.Color1,
)