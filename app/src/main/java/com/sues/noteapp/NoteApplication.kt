package com.sues.noteapp

import android.app.Application
import androidx.room.Room
import com.sues.noteapp.data.local.NoteDatabase

class NoteApplication : Application() {

    val noteDatabase: NoteDatabase by lazy {
        Room.databaseBuilder(
            this.applicationContext,
            NoteDatabase::class.java, "notes-db"
        ).build()
    }


    override fun onCreate() {
        super.onCreate()
    }
}