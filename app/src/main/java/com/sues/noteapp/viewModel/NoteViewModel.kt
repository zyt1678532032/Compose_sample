package com.sues.noteapp.viewModel

import android.net.Uri
import android.util.Log
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
import java.io.File

class NoteViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {

    /// region UiState
    data class UiState(
        val notes: List<Note> = emptyList(),

        val clickedNote: Note? = null,

        val title: String? = null,
        val date: String? = null,
        val content: String? = null,
        val imagePath: String? = null,
        val selectedColor: SelectedColor = SelectedColor.Color1
    )

    var uiState by mutableStateOf(UiState())
        private set

    private val _photoFolder = File(noteRepository.context.cacheDir, "photos").also { it.mkdir() }

    private fun genPhotoFile(): File {
        return File(_photoFolder, "${System.currentTimeMillis()}.jpg")
    }

    fun savePhoto(photoUri: Uri): String {
        val photo = genPhotoFile()
        noteRepository.context.contentResolver.openInputStream(photoUri)?.use { input ->
            photo.outputStream().use {  output ->
                input.copyTo(output)
            }
        }
        return photo.path
    }

    fun insertNote(vararg notes: Note) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                noteRepository.insertNotes(*notes)
            }
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                noteRepository.updateNote(note)
                uiState = uiState.copy(
                    clickedNote = note
                )
                // ⚠️: 这里要注意更新notes，否则会出现点击note修改后，再次点击显示的还是上一次内容的问题
                loadNotes()
            }
        }
    }

    fun setClickedNote(clickedNote: Note?) {
        uiState = uiState.copy(clickedNote = clickedNote)
    }

    suspend fun findNoteById(id: Int): Note? {
        return withContext(Dispatchers.IO) {
            noteRepository.findNoteById(id = id)
        }
    }

    suspend fun loadNotes() {
        withContext(Dispatchers.IO) {
            uiState = uiState.copy(
                notes = noteRepository.getAllNote()
            )
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