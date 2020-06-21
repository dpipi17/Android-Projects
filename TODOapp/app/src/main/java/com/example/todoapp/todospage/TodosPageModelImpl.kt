package com.example.todoapp.todospage

import android.content.Context
import androidx.room.Room
import androidx.room.Transaction
import com.example.todoapp.database.NotesDatabase
import com.example.todoapp.database.note.NoteHelper
import com.example.todoapp.dataclasses.Note

class TodosPageModelImpl(var presenter: TodosPageContract.Presenter, var context: Context) : TodosPageContract.Model {

    private var database: NotesDatabase

    init {
        database = Room.databaseBuilder(context, NotesDatabase::class.java, "notes_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Transaction
    override fun getFilteredNotes(searchWord: String): MutableList<Note> {
        return NoteHelper.fromEntities(database.getNoteDao().getFilteredNotes("%${searchWord}%"), database.getSubNoteDao())
    }

    override fun getAllNotes(): MutableList<Note> {
        return NoteHelper.fromEntities(database.getNoteDao().getAllNotes(), database.getSubNoteDao())
    }


}