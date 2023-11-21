package com.sues.noteapp

import android.app.Application
import androidx.room.Room
import com.sues.noteapp.data.local.NoteDatabase


val Application.noteDatabase: NoteDatabase
    get() {
        return Room.databaseBuilder(
            this,
            NoteDatabase::class.java, "notes-db"
        ).build()
    }
