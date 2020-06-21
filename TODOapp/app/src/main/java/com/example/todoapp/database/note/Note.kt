package com.example.todoapp.database.note

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize
@Entity(tableName = "note_table")
data class Note (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String,
    var pinned: Boolean,
    var lastUpdateDate: Date
) : Parcelable
