package com.example.todoapp.notepage

import com.example.todoapp.dataclasses.NoteWithSubNotes

interface NotePageContract {

    interface View {
        fun showLoader()
        fun hideLoader()
    }

    interface Presenter {
        fun saveNote(noteWithSubNotes: NoteWithSubNotes)
    }

    interface Model {
        fun saveNote(noteWithSubNotes: NoteWithSubNotes)
    }
}