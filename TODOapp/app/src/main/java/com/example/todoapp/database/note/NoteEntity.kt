package com.example.todoapp.database.note

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "note_table")
data class NoteEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val pinned: Boolean,
    val lastUpdateDate: Date
)
