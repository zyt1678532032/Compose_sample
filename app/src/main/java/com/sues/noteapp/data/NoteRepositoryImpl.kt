package com.sues.noteapp.data

import com.sues.noteapp.data.local.Note
import com.sues.noteapp.data.local.NoteDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class NoteRepositoryImpl(
    private val localDatasource: NoteDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : NoteRepository {
    override suspend fun getAllNote(): List<Note> {
        return localDatasource.getAll()
    }

    override suspend fun findByTitle(title: String): Note {
        return localDatasource.findByTitle(title)
    }

    override suspend fun insertNotes(vararg notes: Note) {
        localDatasource.insertAll(*notes)
    }

    override suspend fun deleteNote(note: Note) {
        localDatasource.delete(note)
    }
}