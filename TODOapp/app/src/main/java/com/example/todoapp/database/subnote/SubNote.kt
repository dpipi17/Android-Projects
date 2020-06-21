package com.example.todoapp.database.subnote

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.example.todoapp.database.note.Note
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "sub_note_table",
        foreignKeys = [
            ForeignKey(entity = Note::class,
                parentColumns = ["id"],
                childColumns = ["noteId"],
                onDelete = CASCADE
            )
        ]
)
data class SubNote (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var noteId: Int,
    var description: String,
    var checked: Boolean
) : Parcelable
