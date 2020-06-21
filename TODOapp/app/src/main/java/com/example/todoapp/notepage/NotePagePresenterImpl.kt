package com.example.todoapp.notepage

import android.content.Context
import com.example.todoapp.dataclasses.Note

class NotePagePresenterImpl(var view: NotePageContract.View, var context: Context) : NotePageContract.Presenter {

    private var model: NotePageContract.Model

    init {
        model = NotePageModelImpl(this, context)
    }

    override fun saveNote(note: Note) {
        view.showLoader()
        model.saveNote(note)
        view.hideLoader()
    }

}