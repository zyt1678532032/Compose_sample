package com.sues.noteapp.data

import com.sues.noteapp.data.local.Note


interface NoteRepository {
    suspend fun getAllNote(): List<Note>

    suspend fun findByTitle(title: String): Note?

    suspend fun insertNotes(vararg notes: Note)

    suspend fun deleteNote(note: Note)

    suspend fun updateNote(note: Note)
}