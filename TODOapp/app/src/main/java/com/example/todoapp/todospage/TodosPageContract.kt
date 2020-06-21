package com.example.todoapp.todospage

import com.example.todoapp.dataclasses.Note

interface TodosPageContract {

    interface View {
        fun showLoader()
        fun hideLoader()
    }

    interface Presenter {
        fun getFilteredNotes(searchWord: String) : MutableList<Note>
        fun getAllNotes() : MutableList<Note>
    }

    interface Model {
        fun getFilteredNotes(searchWord: String) : MutableList<Note>
        fun getAllNotes() : MutableList<Note>
    }
}