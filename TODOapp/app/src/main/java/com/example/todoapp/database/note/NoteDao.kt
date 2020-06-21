package com.example.todoapp.database.note

import androidx.room.*
import com.example.todoapp.dataclasses.NoteWithSubNotes

@Dao
interface NoteDao {

    @Query("select * from note_table")
    fun getAllNotes(): MutableList<Note>

    @Query("select * from note_table where id = :noteId")
    fun getNote(noteId: Int): Note

    @Transaction
    @Query("""
            select * 
            from note_table
            where id in (select distinct n.id 
                        from note_table n 
                        left join sub_note_table s 
                        on n.id = s.noteId 
                        Where n.title like :searchWord 
                        or s.description like :searchWord)
            """)
    fun getFilteredNotes(searchWord: String) : MutableList<NoteWithSubNotes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note) : Long

    @Delete
    fun deleteNote(note: Note)
}