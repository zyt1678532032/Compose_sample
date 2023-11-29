package com.sues.noteapp.viewModel

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.sues.noteapp.NoteApplication
import com.sues.noteapp.data.NoteRepository
import com.sues.noteapp.data.NoteRepositoryImpl
import com.sues.noteapp.data.local.Note
import com.sues.noteapp.noteDatabase
import com.sues.noteapp.ui.theme.SelectedColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

data class UiState(
    val notes: List<Note> = emptyList(),

    val clickedNote: Note? = null,

    val title: String? = null,
    val date: String? = null,
    val content: String? = null,
    val imagePath: String? = null,
    val selectedColor: SelectedColor = SelectedColor.Color1
)

// 如果要在ViewModel中使用Context, 可以使用AndroidViewModel
class NoteViewModel(
    application: NoteApplication,
    private val noteRepository: NoteRepository
) : AndroidViewModel(application) {

    private val content: Context
        get() = getApplication()

    /// region UiState
    var uiState by mutableStateOf(UiState())
        private set

    // /data/user/0/com.sues.noteapp/cache/photos
    private val _photoFolder = File(content.cacheDir, "photos").also { it.mkdir() }


    fun savePhoto(photoUri: Uri): String {
        val photo = genPhotoFile()
        content.contentResolver.openInputStream(photoUri)?.use { input ->
            photo.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        return photo.path
    }

    private fun genPhotoFile() = File(_photoFolder, "${System.currentTimeMillis()}.jpg")

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

    companion object {
        val Factory = object : ViewModelProvider.AndroidViewModelFactory() {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY]) as NoteApplication
                return NoteViewModel(
                    application = application,
                    noteRepository = NoteRepositoryImpl(
                        localDatasource = application.noteDatabase.noteDao(),
                    )
                ) as T
            }
        }
    }
}