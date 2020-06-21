package com.example.todoapp.database.subnote

import androidx.room.*

@Dao
interface SubNoteDao {

    @Query("select * from sub_note_table where id = :subNoteId")
    fun getSubNote(subNoteId: Int): SubNote

    @Query("select * from sub_note_table where noteId = :noteId")
    fun getSubNotesByNoteId(noteId: Int) : MutableList<SubNote>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: SubNote)

    @Delete
    fun deleteNote(note: SubNote)
}