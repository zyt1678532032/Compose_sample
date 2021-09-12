package com.sues.noteapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sues.noteapp.entity.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDataBase : RoomDatabase() {
    companion object {
        private var noteDataBase: NoteDataBase? = null

        @Synchronized
        @JvmStatic
        fun getDataBase(context: Context): NoteDataBase {
            if (noteDataBase == null) {
                noteDataBase = Room.databaseBuilder(
                    context, NoteDataBase::class.java, "note_db")
                    .build()
            }
            return noteDataBase!!
        }
    }


}