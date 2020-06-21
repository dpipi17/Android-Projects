package com.example.todoapp.dataclasses

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import com.example.todoapp.database.note.Note
import com.example.todoapp.database.subnote.SubNote
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NoteWithSubNotes(
    @Embedded var note: Note,
    @Relation(
        parentColumn = "id",
        entityColumn = "noteId"
    )
    val subNotes: MutableList<SubNote>
) : Parcelable