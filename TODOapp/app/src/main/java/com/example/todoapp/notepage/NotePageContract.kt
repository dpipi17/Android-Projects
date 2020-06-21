package com.example.todoapp.notepage

import com.example.todoapp.dataclasses.Note

interface NotePageContract {

    interface View {
        fun showLoader()
        fun hideLoader()
    }

    interface Presenter {
        fun saveNote(note: Note)
    }

    interface Model {
        fun saveNote(note: Note)
    }
}