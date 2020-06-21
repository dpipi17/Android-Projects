package com.example.todoapp.todospage

import android.content.Context
import com.example.todoapp.dataclasses.NoteWithSubNotes

class TodosPagePresenterImpl(var view: TodosPageContract.View, var context: Context) : TodosPageContract.Presenter {

    private var model: TodosPageContract.Model

    init {
        model = TodosPageModelImpl(this, context)
    }

    override fun getFilteredNotes(searchWord: String): MutableList<NoteWithSubNotes> {
        view.showLoader()
        val notes =  model.getFilteredNotes(searchWord)
        view.hideLoader()
        return notes
    }
}