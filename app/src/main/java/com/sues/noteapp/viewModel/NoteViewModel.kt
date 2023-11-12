package com.sues.noteapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sues.noteapp.data.NoteRepository
import com.sues.noteapp.data.local.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {

    // 搜索界面Note展示
    private val _noteList: MutableLiveData<List<Note>> = MutableLiveData()
    val noteList: LiveData<List<Note>> = _noteList

    // 可以自定创建一个Scope，但是得自己管理生命周期的状态
    private val customerScope = CoroutineScope(Job() + Dispatchers.IO)

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

    // 和上面的写法类似，建议使用 withContext
    // suspend fun findNoteByTitle2(title: String): Note? {
    //     return viewModelScope.async {
    //         noteRepository.findByTitle(title = title)
    //     }.await()
    // }

    suspend fun getAllNotes(): List<Note> {
        return withContext(Dispatchers.IO) {
            noteRepository.getAllNote()
        }
    }
}