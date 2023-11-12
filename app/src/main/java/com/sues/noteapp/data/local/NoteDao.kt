package com.sues.noteapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getAll(): List<Note>

    @Query(
        """
            SELECT * FROM note 
            WHERE id LIKE :id LIMIT 1
        """
    )
    fun findNoteById(id: Int): Note?

    @Insert
    fun insertAll(vararg notes: Note)

    @Delete
    fun delete(note: Note)

    @Update
    fun update(note: Note)
}