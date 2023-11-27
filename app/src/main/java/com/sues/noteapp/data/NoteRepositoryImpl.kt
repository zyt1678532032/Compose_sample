package com.sues.noteapp.data

import android.content.Context
import com.sues.noteapp.data.local.Note
import com.sues.noteapp.data.local.NoteDao

class NoteRepositoryImpl(
    private val localDatasource: NoteDao,
    override val context: Context
) : NoteRepository {
    override suspend fun getAllNote(): List<Note> {
        return localDatasource.getAll()
    }

    override suspend fun findNoteById(id: Int): Note? {
        return localDatasource.findNoteById(id)
    }

    override suspend fun insertNotes(vararg notes: Note) {
        localDatasource.insertAll(*notes)
    }

    override suspend fun deleteNote(note: Note) {
        localDatasource.delete(note)
    }

    override suspend fun updateNote(note: Note) {
        localDatasource.update(note)
    }
}