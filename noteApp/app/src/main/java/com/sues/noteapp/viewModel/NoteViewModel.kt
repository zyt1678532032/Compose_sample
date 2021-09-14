package com.sues.noteapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sues.noteapp.entity.Note

class NoteViewModel : ViewModel() {

    private val _noteList = MutableLiveData<List<Note>>(notes)
    var noteList: LiveData<List<Note>> = _noteList

    var colorIsShow = MutableLiveData<MutableList<Boolean>>()

    fun addNote(note: Note) {
        _noteList.value = _noteList.value!! + listOf(note)
    }
}

val notes = listOf(
    Note(
        title = "title1",
        subTitle = "subTitle",
        noteText = "的撒hi大使馆蒂萨dsada",
        dateTime = "2021 9-13"
    ),
    Note(
        title = "title1",
        subTitle = "subTitle",
        noteText = "的撒hi大使馆蒂萨dsada\ndsadasddasa爆单赛不带去",
        dateTime = "2021 9-13"
    ),
    Note(
        title = "title1",
        subTitle = "subTitle",
        noteText = "的撒hi大使馆蒂萨dsada\n撒hi大使馆蒂萨dsada\n" +
                "dsadasddasa爆单赛不带",
        dateTime = "2021 9-13"
    )
)