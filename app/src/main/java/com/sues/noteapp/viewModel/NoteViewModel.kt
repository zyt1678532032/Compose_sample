package com.sues.noteapp.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sues.noteapp.data.NoteRepository
import com.sues.noteapp.data.local.Note
import com.sues.noteapp.ui.theme.SelectedColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {

    /// region UiState
    data class UiState(
        val notes: List<Note> = emptyList(),

        val isUpdate: Boolean = false,
        val note: Note? = null,

        val title: String? = null,
        val date: String? = null,
        val content: String? = null,
        val imagePath: String? = null,
        val selectedColor: SelectedColor = SelectedColor.Color1
    )

    var uiState by mutableStateOf(UiState())
        private set


    fun insertNote(vararg notes: Note) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                noteRepository.insertNotes(*notes)
                loadNotes()
            }
        }
    }

    fun updateNote() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                uiState.note?.let {
                    noteRepository.updateNote(
                        it.copy(
                            title = uiState.title,
                            dateTime = uiState.date,
                            noteText = uiState.content,
                            imagePath = uiState.imagePath,
                            selectedColor = uiState.selectedColor,
                        )
                    )
                }

                uiState = uiState.copy(isUpdate = true)
                loadNotes()
            }
        }
    }

    suspend fun findNoteById(id: Int): Note? {
        return withContext(Dispatchers.IO) {
            noteRepository.findNoteById(id = id)
        }
    }

    // 和上面的写法类似，建议使用 withContext
    // suspend fun findNoteByTitle2(title: String): Note? {
    //     return viewModelScope.async {
    //         noteRepository.findByTitle(title = title)
    //     }.await()
    // }

    fun loadNotes() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                uiState = uiState.copy(
                    notes = noteRepository.getAllNote()
                )
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                noteRepository.deleteNote(note)
                loadNotes()
            }
        }
    }
    /// endregion

}