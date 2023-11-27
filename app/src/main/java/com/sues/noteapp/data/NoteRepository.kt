package com.sues.noteapp.data

import android.content.Context
import com.sues.noteapp.data.local.Note


interface NoteRepository {

    val context: Context

    suspend fun getAllNote(): List<Note>

    suspend fun findNoteById(id: Int): Note?

    suspend fun insertNotes(vararg notes: Note)

    suspend fun deleteNote(note: Note)

    suspend fun updateNote(note: Note)
}