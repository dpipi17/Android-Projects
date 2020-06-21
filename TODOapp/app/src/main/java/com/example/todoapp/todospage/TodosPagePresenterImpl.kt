package com.example.todoapp.todospage

import android.content.Context
import com.example.todoapp.dataclasses.Note

class TodosPagePresenterImpl(var view: TodosPageContract.View, var context: Context) : TodosPageContract.Presenter {

    private var model: TodosPageContract.Model

    init {
        model = TodosPageModelImpl(this, context)
    }

    override fun getFilteredNotes(searchWord: String): MutableList<Note> {
        view.showLoader()
        val notes =  model.getFilteredNotes(searchWord)
        view.hideLoader()
        return notes
    }

    override fun getAllNotes(): MutableList<Note> {
        view.showLoader()
        val notes = model.getAllNotes()
        view.hideLoader()
        return notes
    }

}