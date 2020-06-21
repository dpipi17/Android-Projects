package com.example.todoapp.database.subnote

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.example.todoapp.database.note.NoteEntity

@Entity(tableName = "sub_note_table",
        foreignKeys = [
            ForeignKey(entity = NoteEntity::class,
                parentColumns = ["id"],
                childColumns = ["noteId"],
                onDelete = CASCADE
            )
        ]
)
data class SubNoteEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val noteId: Int,
    val description: String,
    val checked: Boolean
)
