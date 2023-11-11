package com.sues.noteapp.viewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sues.noteapp.data.NoteRepository
import com.sues.noteapp.data.local.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {

    // 搜索界面Note展示
    private val _noteList: MutableLiveData<List<Note>> = MutableLiveData()
    val noteList: LiveData<List<Note>> = _noteList

    var imageUri = MutableLiveData<Uri>()

    fun insertNote(vararg notes: Note) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                noteRepository.insertNotes(*notes)
            }
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            noteRepository.updateNote(note = note)
        }
    }

    suspend fun findNoteByTitle(title: String): Note? {
        return withContext(Dispatchers.IO) {
            noteRepository.findByTitle(title = title)
        }
    }

    suspend fun getAllNotes(): List<Note> {
        return withContext(Dispatchers.IO) {
            noteRepository.getAllNote()
        }
    }
}