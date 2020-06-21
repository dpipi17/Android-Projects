package com.example.todoapp.todospage

import com.example.todoapp.dataclasses.NoteWithSubNotes

interface TodosPageContract {

    interface View {
        fun showLoader()
        fun hideLoader()
    }

    interface Presenter {
        fun getFilteredNotes(searchWord: String) : MutableList<NoteWithSubNotes>
    }

    interface Model {
        fun getFilteredNotes(searchWord: String) : MutableList<NoteWithSubNotes>
    }
}