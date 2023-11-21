package com.sues.noteapp

import android.app.Application
import androidx.room.Room
import com.sues.noteapp.data.local.NoteDatabase

class NoteApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}