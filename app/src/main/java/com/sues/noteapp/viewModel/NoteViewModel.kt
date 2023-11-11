package com.sues.noteapp.viewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sues.noteapp.data.NoteRepository
import com.sues.noteapp.data.local.Note
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {

    // 搜索界面Note展示
    private val _noteList: MutableLiveData<List<Note>> = MutableLiveData()
    val noteList: LiveData<List<Note>> = _noteList

    var imageUri = MutableLiveData<Uri>()

    fun updateNote(note: Note) {

    }

    fun addNote(note: Note) {
    }

    fun insertNote(vararg notes: Note) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                noteRepository.insertNotes(*notes)
            }
        }
    }

    suspend fun getAllNotes(): List<Note> {
        val notes = viewModelScope.async {
            withContext(Dispatchers.IO) {
                noteRepository.getAllNote()
            }
        }.await()
        return notes
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