package com.sues.noteapp

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import androidx.room.Room
import com.sues.noteapp.data.local.NoteDatabase


val Application.noteDatabase: NoteDatabase
    get() {
        return Room.databaseBuilder(
            this,
            NoteDatabase::class.java, "notes-db"
        ).build()
    }

fun Context.getActivity(): Activity {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is Activity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    throw IllegalStateException("Permissions should be called in the context of an Activity")
}