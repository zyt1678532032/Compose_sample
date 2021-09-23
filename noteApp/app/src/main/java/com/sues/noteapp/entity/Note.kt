package com.sues.noteapp.entity


import com.sues.noteapp.ui.theme.SelectedColor

data class Note(
    var id: Int? = 1,
    var title: String? = null,
    var dateTime: String,
    var noteText: String,
    var imagePath: String? = null,
    var color: SelectedColor = SelectedColor.Color1,
) {

    override fun toString(): String {
        return "{ \"id\": \"$id\"," +
                " \"title\": \"$title\"," +
                " \"datetime\": \"$dateTime\"," +
                " \"noteText\": \"$noteText\"," +
                " \"imagePath\": \"$imagePath\"," +
                " \"color\": \"$color\"}"

    }
}