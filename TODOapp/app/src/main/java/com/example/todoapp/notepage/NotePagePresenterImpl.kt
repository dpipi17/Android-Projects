package com.example.todoapp.notepage

import android.content.Context
import com.example.todoapp.dataclasses.NoteWithSubNotes

class NotePagePresenterImpl(var view: NotePageContract.View, var context: Context) : NotePageContract.Presenter {

    private var model: NotePageContract.Model

    init {
        model = NotePageModelImpl(this, context)
    }

    override fun saveNote(noteWithSubNotes: NoteWithSubNotes) {
        view.showLoader()
        model.saveNote(noteWithSubNotes)
        view.hideLoader()
    }

}