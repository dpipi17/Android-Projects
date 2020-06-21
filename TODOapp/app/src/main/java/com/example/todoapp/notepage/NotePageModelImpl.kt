package com.example.todoapp.notepage

import android.content.Context
import androidx.room.Room
import com.example.todoapp.database.NotesDatabase
import com.example.todoapp.database.note.NoteHelper
import com.example.todoapp.database.subnote.SubNoteHelper
import com.example.todoapp.dataclasses.Note
import java.util.*

class NotePageModelImpl(var presenter: NotePageContract.Presenter, var context: Context) : NotePageContract.Model {

    private var database: NotesDatabase

    init {
        database = Room.databaseBuilder(context, NotesDatabase::class.java, "notes_database")
            .fallbackToDestructiveMigration()
            .build()
    }


    override fun saveNote(note: Note) {
        if (note.title.isEmpty()) {
            database.getNoteDao().deleteNote(note = NoteHelper.toEntity(note))
            return
        }

        note.lastUpdateDate = Date()
        var newNoteId = database.getNoteDao().insertNote(NoteHelper.toEntity(note))
        note.subNotes.forEach {
            it.noteId = newNoteId.toInt()
            if (it.description.isEmpty()) {
                database.getSubNoteDao().deleteNote(SubNoteHelper.toEntity(it))
            } else {
                database.getSubNoteDao().insertNote(SubNoteHelper.toEntity(it))
            }
        }
    }


}