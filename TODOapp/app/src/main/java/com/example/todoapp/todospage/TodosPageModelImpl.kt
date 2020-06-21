package com.example.todoapp.todospage

import android.content.Context
import androidx.room.Room
import com.example.todoapp.database.NotesDatabase
import com.example.todoapp.dataclasses.NoteWithSubNotes

class TodosPageModelImpl(var presenter: TodosPageContract.Presenter, var context: Context) : TodosPageContract.Model {

    private var database: NotesDatabase

    init {
        database = Room.databaseBuilder(context, NotesDatabase::class.java, "notes_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    override fun getFilteredNotes(searchWord: String): MutableList<NoteWithSubNotes> {
        return database.getNoteDao().getFilteredNotes("%${searchWord}%")
    }

}