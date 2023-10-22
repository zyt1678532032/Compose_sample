package com.sues.noteapp.viewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sues.noteapp.entity.Note

class NoteViewModel : ViewModel() {

    // 搜索界面Note展示
    private val _noteList = MutableLiveData(notes)
    val noteList: LiveData<MutableList<Note>> = _noteList

    var imageUri = MutableLiveData<Uri>()

    fun updateNote(note: Note) {
        val list: MutableList<Note> = _noteList.value!!
        list.forEachIndexed { index, it ->
            if (it.id == note.id) {
                list[index] = note
            }
        }
    }

    fun addNote(note: Note) {
        _noteList.value?.add(note)
    }
}

var noteId = 4

val notes = mutableListOf(
    Note(
        id = 1,
        title = "title1",
        noteText = "的撒hi大使馆蒂萨dsada",
        dateTime = "2021 9-13"
    ),
    Note(
        id = 2,
        title = "title1",
        noteText = "的撒hi大使馆蒂萨dsada",
        dateTime = "2021 9-13"
    ),
    Note(
        id = 3,
        title = "title1",
        noteText = "的撒hi大使馆蒂萨dsada",
        dateTime = "2021 9-13"
    ),
)