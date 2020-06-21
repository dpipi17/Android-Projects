package com.example.todoapp.notepage

import android.content.Context
import androidx.room.Room
import androidx.room.Transaction
import com.example.todoapp.database.NotesDatabase
import com.example.todoapp.dataclasses.NoteWithSubNotes
import java.util.*

class NotePageModelImpl(var presenter: NotePageContract.Presenter, var context: Context) : NotePageContract.Model {

    private var database: NotesDatabase

    init {
        database = Room.databaseBuilder(context, NotesDatabase::class.java, "notes_database")
            .fallbackToDestructiveMigration()
            .build()
    }


    @Transaction
    override fun saveNote(noteWithSubNotes: NoteWithSubNotes) {
        if (noteWithSubNotes.note.title.isEmpty()) {
            database.getNoteDao().deleteNote(note = noteWithSubNotes.note)
            return
        }

        noteWithSubNotes.note.lastUpdateDate = Date()
        var newNoteId = database.getNoteDao().insertNote(noteWithSubNotes.note)
        noteWithSubNotes.subNotes.forEach {
            it.noteId = newNoteId.toInt()
            if (it.description.isEmpty()) {
                database.getSubNoteDao().deleteNote(it)
            } else {
                database.getSubNoteDao().insertNote(it)
            }
        }
    }


}